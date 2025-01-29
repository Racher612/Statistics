package com.project.statistics

import android.app.AppOpsManager
import android.app.AppOpsManager.MODE_ALLOWED
import android.app.AppOpsManager.OPSTR_GET_USAGE_STATS
import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.os.Process
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.project.statistics.navigation.data.NavGraph
import com.project.statistics.ui.theme.StatisticsTheme
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            StatisticsTheme {
                if (checkUsageStatsPermission()){
                    NavGraph(navController = navController)
                } else {
                    startActivity(Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS))
                }
            }
        }
    }

    private fun checkUsageStatsPermission(): Boolean {
        val appOpsManager = getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
        return try {
            val mode = appOpsManager.unsafeCheckOpNoThrow(
                OPSTR_GET_USAGE_STATS,
                Process.myUid(),
                packageName
            )
            mode == MODE_ALLOWED
        } catch (e: SecurityException) {
            false
        }
    }
}

fun ConvertTime(lastTimeUsed : Long): String {
    var date = Date(lastTimeUsed)
    var format = SimpleDateFormat("hh:mm a", Locale.ENGLISH)
    return format.format(date)
}

fun ConvertDate(lastTimeUsed : Long): String {
    var date = Date(lastTimeUsed)
    var format = SimpleDateFormat("dd/mm/yyyy hh:mm a", Locale.ENGLISH)
    return format.format(date)
}
