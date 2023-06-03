package com.shutterfly.pixabaygallery.ui.gallery_list.screen

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.shutterfly.pixabaygallery.R
import com.shutterfly.pixabaygallery.core.getColorFromViewSystem
import com.shutterfly.pixabaygallery.models.network_models.GalleryItem
import com.shutterfly.pixabaygallery.ui.gallery_list.list_item.GalleryListItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.StandardTestDispatcher

@Composable
fun GalleryListScreenContent(
    modifier: Modifier = Modifier,
    imagesPagingItems: LazyPagingItems<GalleryItem>,
    searchTerm: String,
    onSearchTermChanged: (searchTerm: String) -> Unit,
    onSearchImageClicked: () -> Unit,
    onListItemClicked: (GalleryItem) -> Unit
) {
    val gridCells = if (LocalConfiguration.current.orientation
        == Configuration.ORIENTATION_LANDSCAPE
    ) 4 else 3

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                modifier = Modifier
                    .padding(12.dp)
                    .wrapContentWidth()
                    .background(getColorFromViewSystem(color = R.attr.colorSecondary)),
                value = searchTerm,
                onValueChange = { searchTerm ->
                    onSearchTermChanged(searchTerm)
                },
                placeholder = {
                    Text(text = stringResource(id = R.string.search_hint))
                })
            Box(modifier = Modifier.background(Color(0xFF018786))) {}
            Image(
                modifier = Modifier
                    .wrapContentSize()
                    .background(Color(0xFF018786))
                    .clickable {
                        onSearchImageClicked()
                    },
                contentScale = ContentScale.FillBounds,
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = ""
            )
        }
        LazyVerticalGrid(columns = GridCells.Fixed(gridCells), content = {
            items(count = imagesPagingItems.itemCount) { index ->
                val galleryItem = imagesPagingItems[index] ?: return@items
                GalleryListItem(item = galleryItem) { galleryItem ->
                    onListItemClicked(galleryItem)
                }
            }
        })
    }
}

@Preview(showBackground = true)
@Composable
fun GalleryListScreenContentPreview() {
    val data = emptyList<GalleryItem>()
    val flow = MutableStateFlow(PagingData.from(data))
    val pagingItems = flow.collectAsLazyPagingItems(StandardTestDispatcher())
    GalleryListScreenContent(
        imagesPagingItems = pagingItems,
        searchTerm = "android",
        onSearchTermChanged =  {},
        onSearchImageClicked =  {},
        onListItemClicked = {}
    )
}