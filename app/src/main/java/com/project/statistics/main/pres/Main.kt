package com.project.statistics.main.pres

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.project.statistics.main.data.getAppUsageStats

@Composable
fun MainScreen(){
    Text("MainScreen")
    val usageList = getAppUsageStats(LocalContext.current)
    Log.d("ITEMS", usageList.toString())
    if (usageList.isEmpty()){
        CircularProgressIndicator()
    } else {
        LazyColumn {
            usageList.forEach { stat ->
                item {
                    Log.d("ITEM", stat.toString())
                    Text(stat.toString(), modifier = Modifier.border(1.dp, Color.Black, RoundedCornerShape(4.dp)))
                }
            }
        }
    }

}