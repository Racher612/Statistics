package com.project.statistics.common.logic

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

@SuppressLint("DefaultLocale")
fun convertTime(lastTimeUsed : Long): String {
    if (lastTimeUsed == 0L) return "-"

    val hours = TimeUnit.MILLISECONDS.toHours(lastTimeUsed)
    val minutes = TimeUnit.MILLISECONDS.toMinutes(lastTimeUsed) % 60
    val seconds = TimeUnit.MILLISECONDS.toSeconds(lastTimeUsed) % 60

    return String.format("%02d:%02d:%02d", hours, minutes, seconds)
}

fun convertDate(lastTimeUsed : Long): String {
    if (lastTimeUsed == 0L) return "-"

    val date = Date(lastTimeUsed)
    val format = SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.getDefault())
    return format.format(date)
}
