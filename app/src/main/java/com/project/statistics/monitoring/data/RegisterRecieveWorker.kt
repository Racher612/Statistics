package com.project.statistics.monitoring.data

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.work.Worker
import androidx.work.WorkerParameters

class RegisterReceiverWorker(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {

    override fun doWork(): Result {
        val filter = IntentFilter().apply {
            addAction(Intent.ACTION_PACKAGE_ADDED)
            addAction(Intent.ACTION_PACKAGE_REPLACED)
            addDataScheme("package")
        }
        val receiver = AppLaunchReceiver()
        applicationContext.registerReceiver(receiver, filter)
        return Result.success()
    }
}