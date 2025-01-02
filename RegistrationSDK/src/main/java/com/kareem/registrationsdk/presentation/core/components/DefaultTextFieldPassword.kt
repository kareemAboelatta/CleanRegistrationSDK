package com.kareem.registrationsdk.presentation.core.components


import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kareem.registrationsdk.R
import com.kareem.registrationsdk.presentation.core.theme.RegistrationSDKTheme

@Composable
fun DefaultTextFieldPassword(
    modifier: Modifier = Modifier,
    textStyle: TextStyle = MaterialTheme.typography.titleMedium,
    errorText: String? = null,
    hint: String = "Password",
    textInputHint: String = "******",
    textAlign: TextAlign = TextAlign.Start,

    shape: Shape = MaterialTheme.shapes.extraLarge,

    value: String = "",
    onValueChanged: (String) -> Unit = {}
) {
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = modifier
    ) {
        if (hint.isNotEmpty()) {
            Text(
                modifier = Modifier.padding(top = 10.dp),
                text = hint,
            )
        }
        OutlinedTextField(
            value = value,
            onValueChange = onValueChanged,
            modifier = modifier
                .fillMaxWidth()
                .imePadding(),
            textStyle = textStyle,
            shape = shape,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password),
            placeholder = {
                Text(
                    text = " $textInputHint",
                    textAlign = textAlign,
                    color = MaterialTheme.colorScheme.outline
                )
            },
            supportingText = {
                if (errorText.isNullOrEmpty().not())
                    Text(
                        text = errorText!!,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontSize = 12.sp, color = MaterialTheme.colorScheme.error
                        ),
                    )

            },
            trailingIcon = {
                val image =
                    if (passwordVisible) ImageVector.vectorResource(id = R.drawable.ic_invisible)
                    else ImageVector.vectorResource(id = R.drawable.ic_visibile)

                // Please provide localized description for accessibility services
                val description = if (passwordVisible) "Hide password" else "Show password"

                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        modifier = Modifier.size(25.dp),
                        imageVector = image,
                        contentDescription = description
                    )
                }
            },
            visualTransformation = if (passwordVisible) VisualTransformation.None
            else PasswordVisualTransformation()
        )
    }


}

@Preview
@Composable
fun DefaultTextFieldPasswordPreview() {
    RegistrationSDKTheme {
        DefaultTextFieldPassword()
    }
}