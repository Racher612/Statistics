package com.project.statistics.monitoring.data

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class AppLaunchReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action

        Log.d("AppLaunchReceiver", "Received action: $action")

        val sharedPrefsManager = SharedPrefsManager(context)

        if (action == Intent.ACTION_PACKAGE_ADDED || action == Intent.ACTION_PACKAGE_REPLACED) {
            val packageName = intent.data?.encodedSchemeSpecificPart

            if (packageName != null) {
                sharedPrefsManager.updatePackage(packageName)
            }
        }
    }
}