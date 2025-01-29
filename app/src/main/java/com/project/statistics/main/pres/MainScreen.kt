package com.project.statistics.main.pres

import android.app.usage.UsageStats
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.gson.Gson
import com.project.statistics.common.compose.AppIcon
import com.project.statistics.common.logic.convertDate
import com.project.statistics.common.logic.convertTime
import com.project.statistics.navigation.domain.models.Routes

@Composable
fun MainScreen(
    navigateToApp: (String) -> Unit,
    viewModel: MainScreenViewModel = hiltViewModel()
){

    val usageList by viewModel.usageList

    if (!viewModel.loading.value){
        CircularProgressIndicator()
    } else {
        DisplayAppList(
            usageList = usageList,
            navigateToApp,
            viewModel::getAppIcon
        )
    }
}


@Composable
fun DisplayAppList(
    usageList: List<UsageStats>,
    navigateToApp: (String) -> Unit,
    getAppIcon: (String) -> Drawable
){
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
                val icon = getAppIcon(stat.packageName)
                AppCard(
                    usageStat = stat,
                    navigateToApp,
                    icon
                )
            }
        }
    }
}

@Composable
fun AppCard(
    usageStat: UsageStats,
    navigateToApp: (String) -> Unit,
    icon: Drawable
) {
    val descriptionList = listOf("Package Name: ", "Total time used: ", "Last time used: ")

    Log.d("Package: ${usageStat.packageName}", usageStat.lastTimeUsed.toString())

    Card(
        colors =  CardDefaults.cardColors(Color.Green, Color.Black),
        shape = RoundedCornerShape(4.dp),
        modifier = Modifier
            .padding(4.dp)
            .clickable {
                navigateToApp(Routes.App.route + "/${usageStat.packageName}")
            }
            .fillMaxWidth()
            .clip(RoundedCornerShape(4.dp))
            .border(1.dp, Color.Black, RoundedCornerShape(4.dp))
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
        ){
            AppIcon(drawable = icon)
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
        ) {
            listOf(
                usageStat.packageName,
                convertTime(usageStat.totalTimeVisible),
                convertDate(usageStat.lastTimeUsed)
            ).forEachIndexed{num, data ->

                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(4.dp)
                ) {
                    Text(text = descriptionList[num])
                    Text(text = data)
                }
            }
        }
    }
}