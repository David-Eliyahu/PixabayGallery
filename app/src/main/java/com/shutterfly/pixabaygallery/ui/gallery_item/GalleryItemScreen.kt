package com.shutterfly.pixabaygallery.ui.gallery_item

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.ramcosta.composedestinations.annotation.Destination
import com.shutterfly.pixabaygallery.R
import com.shutterfly.pixabaygallery.models.network_models.GalleryItem

@Destination
@Composable
fun GalleryItemScreen(item: GalleryItem) {
    val vector = ImageVector.vectorResource(id = R.drawable.ic_favorite_full)
    Column(modifier = Modifier.fillMaxSize()) {
        Image(
            imageVector = ImageVector.vectorResource(
                //TODO: change to empty vector if not favorite
                id = R.drawable.ic_favorite_full
            ),
            contentDescription = null,
            modifier = Modifier.wrapContentSize()
        )
        AsyncImage(
            model = item.previewUrl,
            contentDescription = null,
            Modifier.weight(1f)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GalleryItemScreenPreview() {
    GalleryItemScreen(GalleryItem(1, "", 9))
}