package com.shutterfly.pixabaygallery.ui.gallery_list.list_item

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.shutterfly.pixabaygallery.models.network_models.GalleryItem

@Composable
fun GalleryListItem(item: GalleryItem, onClick : (GalleryItem) -> Unit) {
    AsyncImage(
        model = item.previewUrl,
        contentDescription = "",
        contentScale = ContentScale.FillBounds,
        modifier = Modifier.padding(2.dp).size(100.dp).clickable {
            onClick(item)
        }
    )
}

@Preview(showBackground = true)
@Composable
fun GalleryListItemPreview() {
    GalleryListItem(GalleryItem(1, "", 9)) {}
}