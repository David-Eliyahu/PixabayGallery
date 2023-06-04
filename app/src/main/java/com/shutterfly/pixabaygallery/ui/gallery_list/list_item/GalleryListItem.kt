package com.shutterfly.pixabaygallery.ui.gallery_list.list_item

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import com.shutterfly.pixabaygallery.models.network_models.GalleryItem

@Composable
fun GalleryListItem(item: GalleryItem, onClick: (GalleryItem) -> Unit) {
    Column(modifier = Modifier
        .wrapContentSize()
        .padding(2.dp)
    ) {
        Text(
            modifier = Modifier
                .background(Color.LightGray)
                .fillMaxWidth()
                .zIndex(1f),
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            text = item.likes.toString()
        )
        AsyncImage(
            model = item.previewUrl,
            contentDescription = "",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .height(100.dp)
                .fillMaxWidth()
                .clickable {
                    onClick(item)
                }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GalleryListItemPreview() {
    GalleryListItem(GalleryItem(1, "", 9)) {}
}