@file:OptIn(ExperimentalMaterial3Api::class)

package com.shutterfly.pixabaygallery.ui.gallery_list.top_bar

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.shutterfly.pixabaygallery.R

@Composable
fun GalleryListScreenTopBar() {
    TopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.app_name),
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
        },
        colors = TopAppBarDefaults
            .topAppBarColors(containerColor = Color(0xFF6200EE))
    )
}

@Preview(showBackground = true)
@Composable
fun GalleryListScreenTopBarPreview() {
    GalleryListScreenTopBar()
}