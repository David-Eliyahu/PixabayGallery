package com.shutterfly.pixabaygallery.core.utils

import android.content.Context
import android.util.TypedValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import kotlinx.coroutines.CoroutineScope

@Composable
fun getAttributeFromViewSystem(color: Int): Color {
    return colorResource(LocalContext.current.getColorFromAttrs(color).resourceId)
}

private fun Context.getColorFromAttrs(attr: Int): TypedValue {
    return TypedValue().apply {
        theme.resolveAttribute(attr, this, true)
    }
}

@Composable
fun SingleTimeLaunchedEffect(block: suspend CoroutineScope.() -> Unit) = LaunchedEffect(key1 = true, block = block)