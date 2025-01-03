package com.kareem.registrationsdk.presentation.core.launcher

import android.content.Context
import android.content.Intent
import com.kareem.registrationsdk.presentation.RegistrationActivity

object RegistrationSDKLauncher {
    fun launchRegistrationSDK(context: Context) {
        val intent = Intent(context, RegistrationActivity::class.java)
        context.startActivity(intent)
    }
}
