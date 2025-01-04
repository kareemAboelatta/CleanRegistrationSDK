package com.kareem.registrationsdk.presentation.screens.register.second_step_screen

import android.Manifest
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import com.kareem.registrationsdk.detection.FaceAnalysisProcessor
import com.kareem.registrationsdk.detection.FaceWorkflow
import com.kareem.registrationsdk.detection.tasks.DetectionStep
import com.kareem.registrationsdk.detection.tasks.SmileDetectionStep
import com.kareem.registrationsdk.presentation.core.camera_utils.capturePhoto
import com.kareem.registrationsdk.presentation.core.navigation.LocalNavController
import com.kareem.registrationsdk.presentation.core.navigation.Screens
import com.kareem.registrationsdk.presentation.core.navigation.sharedViewModel
import com.kareem.registrationsdk.presentation.screens.register.second_step_screen.viewmodel.SecondStepEvent
import com.kareem.registrationsdk.presentation.screens.register.second_step_screen.viewmodel.SecondStepState
import com.kareem.registrationsdk.presentation.screens.register.second_step_screen.viewmodel.SecondStepUiEffect
import com.kareem.registrationsdk.presentation.screens.register.second_step_screen.viewmodel.SecondStepViewModel
import com.kareem.registrationsdk.presentation.screens.register.shared_viewmodel.RegistrationUiEffect
import com.kareem.registrationsdk.presentation.screens.register.shared_viewmodel.SharedRegistrationEvent
import com.kareem.registrationsdk.presentation.screens.register.shared_viewmodel.SharedRegistrationViewModel
import kotlinx.coroutines.flow.collectLatest

/**
 * [SecondStepScreen]
 *
 * Allows the user to capture a selfie after a smile is detected.
 * - Requests camera permission if not granted
 * - Displays a circular camera preview using CameraX
 * - Uses [FaceWorkflow] to detect a smile ([SmileDetectionStep])
 * - On successful detection, automatically captures a photo and saves it
 */
@Composable
fun SecondStepScreen(
    navController: NavController = LocalNavController.current,
    viewModel: SecondStepViewModel = hiltViewModel(),
    sharedRegistrationViewModel: SharedRegistrationViewModel = navController.sharedViewModel()
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    // Camera controller + preview
    val cameraController = remember { LifecycleCameraController(context) }
    val previewView = remember { PreviewView(context) }

    fun stopCamera() {
        cameraController.unbind()
        previewView.controller = null
    }

    // Bind camera lifecycle
    LaunchedEffect(Unit) {
        cameraController.bindToLifecycle(lifecycleOwner)
        cameraController.cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA
        previewView.controller = cameraController
    }

    // Track camera permission
    var hasCameraPermission by remember { mutableStateOf(false) }
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        hasCameraPermission = granted
    }

    // Request camera permission on first composition
    LaunchedEffect(Unit) {
        permissionLauncher.launch(Manifest.permission.CAMERA)
    }

    // Collect side effects (UI events) from ViewModel
    LaunchedEffect(Unit) {
        viewModel.effectFlow.collectLatest { effect ->
            when (effect) {
                is SecondStepUiEffect.CapturePhoto -> {
                    cameraController.capturePhoto(context) { file ->
                        // Save file path, then trigger final user data save
                        viewModel.onEvent(SecondStepEvent.OnPhotoCaptured(file.absolutePath))
                    }
                }

                is SecondStepUiEffect.ShowFlowError -> {
                    // Show error message
                    Log.d("TAG", "ShowFlowError: ${effect.message} ");
                }

                is SecondStepUiEffect.PhotoCaptured -> {
                    sharedRegistrationViewModel.onEvent(
                        SharedRegistrationEvent.OnImageChanged(effect.photoPath)
                    )
                    sharedRegistrationViewModel.onEvent(SharedRegistrationEvent.SaveUserData)

                }
            }
        }
    }

    // Collect side effects (UI events) from shared view model
    LaunchedEffect(Unit) {
        sharedRegistrationViewModel.effectFlow.collectLatest { effect ->
            when (effect) {
                is RegistrationUiEffect.ShowError -> {
                    Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                }

                RegistrationUiEffect.UserDataSaved -> {
                    stopCamera()
                    navController.navigate(Screens.RegistrationSuccessDialog)
                }
            }
        }
    }


    // UI Layout
    Surface(modifier = Modifier.fillMaxSize()) {
        if (!hasCameraPermission) {
            // If camera permission not granted, prompt user to enable
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Button(onClick = { permissionLauncher.launch(Manifest.permission.CAMERA) }) {
                    Text(text = "Enable Camera")
                }
            }
        } else {
            // Setup the face workflow steps
            val workflow by remember {
                derivedStateOf {
                    FaceWorkflow(
                        steps = listOf(
                            viewModel.state.detectionMethodType.detectionStep
                        )
                    )
                }
            }

            // Listen for step changes in the workflow
            LaunchedEffect(workflow) {
                workflow.setListener(
                    object : FaceWorkflow.FaceWorkflowListener {
                        override fun onStepStarted(step: DetectionStep, stepIndex: Int) {
                            viewModel.onEvent(SecondStepEvent.OnFlowStepStarted(step, stepIndex))
                        }

                        override fun onStepCompleted(step: DetectionStep, isFinalStep: Boolean) {
                            viewModel.onEvent(SecondStepEvent.OnFlowStepFinished(step, isFinalStep))
                        }

                        override fun onWorkflowError(error: FaceWorkflow.WorkflowError) {
                            val message = when (error) {
                                FaceWorkflow.WorkflowError.NoFacesFound ->
                                    "No face detected. Please position your face in front camera."

                                FaceWorkflow.WorkflowError.MultipleFacesFound ->
                                    "Multiple faces detected. Please ensure only one face is visible."
                            }
                            viewModel.onEvent(SecondStepEvent.OnFlowError(message))
                        }
                    }
                )
            }

            // Attach face analysis to CameraController
            cameraController.setImageAnalysisAnalyzer(
                ContextCompat.getMainExecutor(context),
                FaceAnalysisProcessor(workflow)
            )

            // Compose UI
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.onBackground)
            ) {
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = viewModel.state.detectionMethodType.detectionStep.stepDescription,
                        color = Color.White,
                        style = MaterialTheme.typography.titleLarge
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Box(
                        modifier = Modifier
                            .size(300.dp)
                            .clip(CircleShape)
                    ) {
                        AndroidView(
                            factory = { previewView },
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = viewModel.state.statusMessage,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )

                    DetectionMethod(
                        selectedType = viewModel.state.detectionMethodType,
                        onSelect = {
                            viewModel.onEvent(SecondStepEvent.OnDetectionStepTypeChanged(it))
                        }
                    )

                }
            }
        }
    }
}


@Composable
fun DetectionMethod(
    modifier: Modifier = Modifier,
    selectedType: SecondStepState.DetectionStepType,
    onSelect: (SecondStepState.DetectionStepType) -> Unit
) {
    Text(text = "Choose Detection Method", color = Color.White)
    SecondStepState.DetectionStepType.entries.forEach { type ->
        Row(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth()
                .selectable(
                    selected = type == selectedType,
                    onClick = {
                        onSelect(type)
                    }
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {

            RadioButton(
                colors = RadioButtonDefaults.colors(
                    selectedColor = Color.White
                ),
                selected = type == selectedType,
                onClick = {
                    onSelect(type)
                }
            )

            Text(
                text = type.detectionStep.stepTitle,
                color = Color.White,
            )
        }

    }
}