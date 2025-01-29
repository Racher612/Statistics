package com.project.statistics.common.compose

import android.graphics.drawable.Drawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap

@Composable
fun AppIcon(
    drawable: Drawable,
    size: Int = 48,
    modifier: Modifier = Modifier
) {
    val bitmap = remember(drawable) { drawable.toBitmap().asImageBitmap() }

    Image(
        bitmap = bitmap,
        contentDescription = "App Icon",
        modifier = modifier.size(size.dp)
    )
}