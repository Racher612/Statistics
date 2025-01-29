package com.project.statistics.monitoring.data

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            // Запускаем WorkManager, чтобы зарегистрировать AppLaunchReceiver
            val workRequest = OneTimeWorkRequestBuilder<RegisterReceiverWorker>().build()
            WorkManager.getInstance(context).enqueue(workRequest)
        }
    }
}