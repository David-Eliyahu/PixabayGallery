package com.shutterfly.pixabaygallery.core

import android.content.Context
import android.util.TypedValue
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource

@Composable
fun getColorFromViewSystem(color: Int): Color {
    return colorResource(LocalContext.current.getColorFromAttrs(color).resourceId)
}

private fun Context.getColorFromAttrs(attr: Int): TypedValue {
    return TypedValue().apply {
        theme.resolveAttribute(attr, this, true)
    }
}