package com.kareem.registrationsdk.presentation.screens.register.success_dialog

import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp


@Composable
fun RegistrationSuccessDialog(
    activity: ComponentActivity,
) {

    Box(
        modifier = Modifier
            .clip(MaterialTheme.shapes.large)
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier.padding(10.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Registration Complete",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(vertical = 10.dp)
            )

            Text(
                text = "Thank you for your beautiful smile! You have been registered successfully.",
                style = MaterialTheme.typography.bodyMedium
            )

            Button(
                onClick = {
                    activity.finish()
                }
            ) {
                Text(text = "OK")
            }

        }
    }

}