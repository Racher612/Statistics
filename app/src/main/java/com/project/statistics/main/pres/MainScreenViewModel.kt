package com.project.statistics.main.pres

import android.app.usage.UsageStats
import android.content.Context
import android.graphics.drawable.Drawable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.statistics.R
import com.project.statistics.appdataproviders.data.StatisticProvider
import com.project.statistics.appdataproviders.data.AppIconProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainScreenViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {
    private val privateUsageList = mutableStateOf(listOf<UsageStats>())
    var usageList : State<List<UsageStats>> = privateUsageList

    private var statisticProvider : StatisticProvider? = null
    private var appIconProvider : AppIconProvider? = null

    private val privateLoading = mutableStateOf(false)
    val loading: State<Boolean> = privateLoading

    init {
        statisticProvider = StatisticProvider(context)
        appIconProvider = AppIconProvider(context)
    }

    private fun getUsageStats(){
        viewModelScope.launch {
            privateUsageList.value = statisticProvider?.getUsageStats() ?: listOf()

            if (privateUsageList.value.isNotEmpty()){
                privateLoading.value = true
            }
        }
    }

    fun getAppIcon(packageName : String) : Drawable {
        return appIconProvider?.getAppIcon(packageName) ?: ContextCompat.getDrawable(context, R.drawable.none)!!
    }

    init {
        getUsageStats()
    }
}