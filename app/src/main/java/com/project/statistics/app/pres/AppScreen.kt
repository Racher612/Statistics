package com.project.statistics.app.pres

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.project.statistics.R
import com.project.statistics.common.compose.AppIcon
import com.project.statistics.common.logic.convertDate
import com.project.statistics.common.logic.convertTime

@Composable
fun AppScreen(
    packageName : String,
    navigateBack : () -> Unit,
    appScreenViewModel: AppScreenViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        appScreenViewModel.loadUsageData(packageName)
    }

    val usageStat by appScreenViewModel.usageStats

    val descriptionList = listOf(
        stringResource(R.string.package_name),
        stringResource(R.string.total_time_used),
        stringResource(R.string.last_time_used),
        stringResource(R.string.total_fime_in_foreground),
        stringResource(R.string.time_used_by_foreground_services),
        stringResource(R.string.total_number_of_launches)
    )

    Scaffold(
        topBar = { ContainerBack(navigateBack) },
    ) {paddingValues ->
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Green)
                .padding(paddingValues)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                AppIcon(
                    drawable = appScreenViewModel.appIcon.value!!,
                    size = 120
                )
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
            ) {
                listOf(
                    usageStat?.packageName ?: stringResource(R.string.not_found),
                    convertTime(usageStat?.totalTimeVisible ?: 0),
                    convertDate(usageStat?.lastTimeUsed ?: 0),
                    convertTime(usageStat?.totalTimeInForeground ?: 0),
                    convertTime(usageStat?.totalTimeForegroundServiceUsed ?: 0)        ,
                    appScreenViewModel.launchNumber.value.toString()
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
            PopUpWindow(viewModel = appScreenViewModel)
            Button(
                onClick = appScreenViewModel::openWindow,
                colors = ButtonColors(Color.White, Color.Black, Color.White, Color.Black)
            ) {
                Text(stringResource(R.string.export_to_json))
            }
        }
    }
}


@Composable
fun ContainerBack(
    navigateBack: () -> Unit
){
    Box(
        contentAlignment = Alignment.CenterStart,
        modifier = Modifier
            .background(Color.Green)
            .fillMaxWidth()
    ) {
        Icon(
            painter = painterResource(id = R.drawable.arrow_back),
            contentDescription = stringResource(R.string.back),
            modifier = Modifier
                .size(32.dp)
                .clickable {
                    navigateBack()
                }
        )
    }
}

@Composable
fun PopUpWindow(
    viewModel: AppScreenViewModel
){
    val scrollState = rememberScrollState()

    if (viewModel.popUp.value){
        Dialog(onDismissRequest = viewModel::openWindow) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                TextField(
                    enabled = false,
                    value = viewModel.getJSON(),
                    modifier = Modifier.verticalScroll(scrollState),
                    onValueChange = {}
                )
                Row {
                    Button(
                        onClick = viewModel::copyJSONtoClipboard,
                        colors = ButtonColors(Color.White, Color.Black, Color.White, Color.Black)
                    ) {
                        Text(stringResource(R.string.copy))
                    }
                    Button(
                        onClick = viewModel::openWindow,
                        colors = ButtonColors(Color.White, Color.Black, Color.White, Color.Black)
                    ) {
                        Text(text = stringResource(R.string.close))
                    }
                }
            }
        }
    }
}