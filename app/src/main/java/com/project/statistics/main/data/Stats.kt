package com.project.statistics.main.data

import android.annotation.SuppressLint
import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresApi
import java.util.*

fun getInstalledApps(context: Context): List<String> {
    val packageManager = context.packageManager
    val apps = packageManager.getInstalledApplications(PackageManager.GET_META_DATA)

    // Возвращаем список имен пакетов
    return apps.map { it.packageName }
}

@RequiresApi(Build.VERSION_CODES.LOLLIPOP_MR1)
fun getUsageStatsForApp(context: Context, packageName: String): UsageStats? {
    val usageStatsManager = context.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager

    // Период, за который нужна статистика (например, последние 24 часа)
    val endTime = System.currentTimeMillis()
    val startTime = endTime - 1000 * 3600 * 24 // 24 часа назад

    // Получаем статистику за указанный период
    val stats = usageStatsManager.queryUsageStats(
        UsageStatsManager.INTERVAL_DAILY, startTime, endTime
    )

    // Ищем статистику для конкретного приложения
    return stats.find { it.packageName == packageName }
}