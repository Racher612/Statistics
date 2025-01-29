package com.project.statistics.appdataproviders.data

import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import android.content.Context
import android.icu.util.Calendar
import dagger.hilt.android.qualifiers.ApplicationContext


class StatisticProvider(
    @ApplicationContext private val context: Context
) {
    fun getUsageStats() : List<UsageStats> {
        val usageStatsManager = context.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
        var cal = Calendar.getInstance()
        cal.add(Calendar.DAY_OF_MONTH, -1)
        var queryUsageStats : List<UsageStats> = usageStatsManager.queryUsageStats(
            UsageStatsManager.INTERVAL_DAILY,
            cal.timeInMillis,
            System.currentTimeMillis()
        )
        return queryUsageStats
    }

    fun getUsageStatsByPackage(packageName: String): UsageStats? {
        return getUsageStats().find { it.packageName == packageName }
    }
}
