package com.shutterfly.pixabaygallery.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun FullImageScreen(
    previewUrl: String,
    navigateBack: () -> Unit = {}
) {
    Box {
        IconButton(
            modifier = Modifier.padding(8.dp),
            onClick = { navigateBack() }
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = null
            )
        }
        if (previewUrl.isNotEmpty()) {
            AsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = previewUrl,
                contentDescription = null
            )
        }
    }
}