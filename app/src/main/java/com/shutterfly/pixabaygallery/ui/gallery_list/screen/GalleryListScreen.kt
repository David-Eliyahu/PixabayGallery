package com.shutterfly.pixabaygallery.ui.gallery_list.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.asFlow
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.shutterfly.pixabaygallery.ui.destinations.GalleryItemScreenDestination
import com.shutterfly.pixabaygallery.ui.gallery_list.top_bar.GalleryListScreenTopBar
import com.shutterfly.pixabaygallery.ui.gallery_list.viewmodel.GalleryViewModel
import com.shutterfly.pixabaygallery.ui.gallery_list.viewmodel.GalleryViewModelFactory

@RootNavGraph(start = true)
@Destination
@Composable
fun GalleryListScreen(
    viewmodel: GalleryViewModel = viewModel(
        factory = GalleryViewModelFactory()
    ),
    navigator: DestinationsNavigator
) {

    val imagesPagingItems = viewmodel.imageListObservable.asFlow().collectAsLazyPagingItems()
    val searchTerm by viewmodel.searchTextFieldValue.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            GalleryListScreenTopBar()
        }, content = { paddingValues ->
            GalleryListScreenContent(
                modifier = Modifier.padding(paddingValues),
                imagesPagingItems = imagesPagingItems,
                searchTerm = searchTerm,
                onSearchTermChanged = { searchTerm ->
                    viewmodel.onSearchTermChanged(searchTerm)
                },
                onSearchImageClicked = {
                    viewmodel.onSearchButtonClicked()
                },
                onListItemClicked = { galleryItem ->
                    navigator.navigate(GalleryItemScreenDestination(galleryItem))
                }
            )
        })
}


