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
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.project.statistics.ui.theme.StatisticsTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            StatisticsTheme {
                if (checkUsageStatsPermission()){
                    displayAppList(usageList = getUsageStats())
                } else {
                    startActivity(Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS))
                }
            }
        }
    }

    private fun getUsageStats() : List<UsageStats> {
        val usageStatsManager = getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
        var cal = Calendar.getInstance()
        cal.add(Calendar.DAY_OF_MONTH, -1)
        var queryUsageStats : List<UsageStats> = usageStatsManager.queryUsageStats(
            UsageStatsManager.INTERVAL_DAILY,
            cal.timeInMillis,
            System.currentTimeMillis()
        )
        return queryUsageStats
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
    var format = SimpleDateFormat("dd/mm/yyyy hh:mm a", Locale.ENGLISH)
    return format.format(date)
}

@Composable
fun displayAppList(
    usageList : List<UsageStats>
){
    if (usageList.isEmpty()){
        CircularProgressIndicator()
    } else {
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .background(Color.White)
                .padding(4.dp)
                .fillMaxSize()
        ) {
            usageList.forEach { stat ->
                item {
                    Log.d("ITEM", stat.toString())
                    AppCard(usageStat = stat)
                }
            }
        }
    }
}

@Composable
fun AppCard(
    usageStat : UsageStats
){
    val descriptionList = listOf("Package Name: ", "Total time used: ", "Last time used: ")
    Card(
        colors =  CardDefaults.cardColors(Color.Green, Color.Black),
        shape = CardDefaults.elevatedShape,
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.padding(4.dp)
        ) {
            listOf(
                usageStat.packageName,
                ConvertTime(usageStat.totalTimeVisible),
                ConvertTime(usageStat.lastTimeUsed)
            ).forEachIndexed{num, data ->
                Text(text = descriptionList[num] + data.toString())
            }
        }

    }
}
