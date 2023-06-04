package com.shutterfly.pixabaygallery.ui.gallery_item.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.ramcosta.composedestinations.annotation.Destination
import com.shutterfly.pixabaygallery.R
import com.shutterfly.pixabaygallery.core.utils.SingleTimeLaunchedEffect
import com.shutterfly.pixabaygallery.models.network_models.GalleryItem
import com.shutterfly.pixabaygallery.ui.gallery_item.viewmodel.GalleryItemViewModel

@Destination
@Composable
fun GalleryItemScreen(
    item: GalleryItem,
    viewmodel: GalleryItemViewModel = viewModel(
        factory = GalleryItemViewModel.GalleryItemViewModelFactory(),
    ),
) {
    SingleTimeLaunchedEffect {
        viewmodel.getIsFavoriteItem(item)
    }
    val isFavorite by viewmodel.isFavoriteItem.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Image(
            imageVector = ImageVector.vectorResource(
                id = if (isFavorite) R.drawable.ic_favorite_full else R.drawable.ic_favorite_empty
            ),
            contentDescription = null,
            modifier = Modifier.wrapContentSize()
        )
        AsyncImage(
            model = item.previewUrl,
            contentDescription = null,
            Modifier
                .weight(1f)
                .clickable {
                    viewmodel.changeFavorite(item)
                }
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Preview(showBackground = true)
@Composable
fun GalleryItemScreenPreview() {
    GalleryItemScreen(GalleryItem(1, "", 9))
}