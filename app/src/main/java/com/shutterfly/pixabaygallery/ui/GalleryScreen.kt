package com.shutterfly.pixabaygallery.ui

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.shutterfly.pixabaygallery.repositories.GalleryRepository
import com.shutterfly.pixabaygallery.viewmodels.GalleryViewModel
import com.shutterfly.pixabaygallery.viewmodels.GalleryViewModelFactory

@Composable
fun GalleryScreen(
    viewmodel: GalleryViewModel = viewModel(factory =
        GalleryViewModelFactory(GalleryRepository())
    )
) {
    

}

