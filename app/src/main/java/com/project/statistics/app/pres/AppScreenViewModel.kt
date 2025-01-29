package com.project.statistics.app.pres

import android.app.usage.UsageStats
import android.content.Context
import android.graphics.drawable.Drawable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import com.project.statistics.R
import com.project.statistics.appdataproviders.data.AppIconProvider
import com.project.statistics.appdataproviders.data.StatisticProvider
import com.project.statistics.monitoring.data.SharedPrefsManager
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class AppScreenViewModel @Inject constructor(
    @ApplicationContext private val context: Context
): ViewModel() {
    private val privateLaunchNumber = mutableStateOf(0)
    var launchNumber = privateLaunchNumber

    private val privateUsageStats = mutableStateOf<UsageStats?>(null)
    var usageStats : State<UsageStats?> = privateUsageStats

    private val privateAppIcon = mutableStateOf(ContextCompat.getDrawable(context,R.drawable.none))
    var appIcon : State<Drawable?> = privateAppIcon

    private var appIconProvider : AppIconProvider? = null
    private var statisticProvider : StatisticProvider? = null
    private var sharedPrefsManager : SharedPrefsManager? = null

    init{
        appIconProvider = AppIconProvider(context)
        statisticProvider = StatisticProvider(context)
        sharedPrefsManager = SharedPrefsManager(context)
    }

    fun loadUsageData(packageName: String){
        val usageStats = statisticProvider?.getUsageStatsByPackage(packageName)
        privateUsageStats.value = usageStats
        getAppIcon(privateUsageStats.value?.packageName ?: "")
        privateLaunchNumber.value = getLaunchNumber(usageStats?.packageName ?: "")
    }

    private fun getAppIcon(packageName : String) {
        privateAppIcon.value = appIconProvider?.getAppIcon(packageName) ?: ContextCompat.getDrawable(context, R.drawable.none)!!
    }

    private fun getLaunchNumber(packageName: String) : Int{
        return sharedPrefsManager?.getLaunchNumber(packageName) ?: 0
    }
}