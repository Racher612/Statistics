package com.project.statistics.main.pres

import android.app.usage.UsageStats
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.project.statistics.ConvertTime

@Composable
fun MainScreen(
    viewModel: MainScreenViewModel = hiltViewModel()
){

    val usageList by viewModel.usageList

    if (!viewModel.loading.value){
        CircularProgressIndicator()
    } else {
        DisplayAppList(usageList = usageList)
    }
}


@Composable
fun DisplayAppList(
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
