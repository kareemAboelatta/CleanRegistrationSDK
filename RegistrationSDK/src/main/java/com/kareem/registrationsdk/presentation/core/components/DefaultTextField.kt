package com.kareem.registrationsdk.presentation.core.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun DefaultTextField(
    modifier: Modifier = Modifier,
    hint: String = "",
    textInputHint: String = "",
    isLetterOnly: Boolean = false,
    icon: Int? = null,
    minLines: Int = 1,
    maxLines: Int = minLines + 1,
    shape: Shape = MaterialTheme.shapes.extraLarge,
    keyboardType: KeyboardType,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    error: String? = null,
    isEnabled: Boolean = true,
    textAlign: TextAlign = TextAlign.Start,
    textState: String? = "",
    text: (String) -> Unit = {}
) {


    Column(
        modifier = modifier
    ) {

        if (hint.isNotEmpty())
            Text(
                modifier = Modifier.padding(top = 10.dp),
                text = hint,
            )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp),
            enabled = isEnabled,
            shape = shape,
            value = textState ?: "",
            minLines = minLines,
            onValueChange = {
                if (isLetterOnly) {
                    if (it.all { it.isLetter() })
                        text(it)
                } else
                    text(it)
            },

            placeholder = {
                Text(
                    text = " $textInputHint",
                    textAlign = textAlign,
                    color = MaterialTheme.colorScheme.outline
                )
            },
            textStyle = MaterialTheme.typography.bodyMedium,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.tertiary,       // Border color when focused
                unfocusedBorderColor = MaterialTheme.colorScheme.outline,     // Border color when not focused
            ),
            maxLines = maxLines,
            visualTransformation = visualTransformation,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = keyboardType,
                imeAction = ImeAction.Done
            ),

            trailingIcon = {
                icon?.let {
                    Icon(
                        painter = painterResource(it),
                        contentDescription = null,
                        tint = Color.Unspecified
                    )
                }
            },
            isError = error != null,
            supportingText = {
                if (error.isNullOrEmpty().not())
                    Text(
                        text = error!!,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontSize = 12.sp, color = MaterialTheme.colorScheme.error
                        ),
                    )
            }
        )


    }
}