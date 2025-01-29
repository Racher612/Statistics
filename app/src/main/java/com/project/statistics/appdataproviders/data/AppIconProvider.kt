package com.project.statistics.appdataproviders.data

import android.content.Context
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import dagger.hilt.android.qualifiers.ApplicationContext


class AppIconProvider(
    @ApplicationContext private val context: Context
) {
    fun getAppIcon(packageName: String): Drawable? {
        return try {
            val packageManager = context.packageManager
            packageManager.getApplicationIcon(packageName)
        } catch (e: PackageManager.NameNotFoundException) {
            null
        }
    }
}