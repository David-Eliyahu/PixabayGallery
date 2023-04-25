package com.shutterfly.pixabaygallery.ui

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.BottomStart
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.compose.LazyPagingItems
import coil.compose.AsyncImage
import com.shutterfly.pixabaygallery.R
import com.shutterfly.pixabaygallery.models.GalleryItem

@Composable
fun GalleryScreen(
    galleryItems: LazyPagingItems<GalleryItem>,
    onSearch: (String) -> Unit = {},
    navigateToImage: (GalleryItem) -> Unit = {}
) {
    val configuration = LocalConfiguration.current
    val numberOfColumns =
        if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) 4 else 3
    var searchKeyword by remember {
        mutableStateOf("")
    }
    val applySearch = {
        onSearch(searchKeyword)
    }
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier.height(56.dp),
            verticalAlignment = CenterVertically
        ) {
            TextField(
                modifier = Modifier.weight(1f),
                value = searchKeyword,
                onValueChange = {
                    searchKeyword = it
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        applySearch()
                    }
                )
            )
            IconButton(
                enabled = searchKeyword.isNotEmpty(),
                onClick = {
                    applySearch()
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search icon"
                )
            }
        }
        LazyVerticalGrid(
            modifier = Modifier.fillMaxWidth(),
            columns = GridCells.Fixed(numberOfColumns),
            contentPadding = PaddingValues(top = 8.dp)
        ) {
            items(galleryItems.itemCount) { index ->
                Box {
                    galleryItems[index]?.let { galleryItem ->
                        AsyncImage(
                            modifier = Modifier
                                .height(100.dp)
                                .padding(2.dp)
                                .background(color = Color.LightGray)
                                .clickable {
                                    navigateToImage(galleryItem)
                                },
                            model = galleryItem.previewUrl,
                            contentScale = ContentScale.Crop,
                            contentDescription = null
                        )
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    brush = Brush.verticalGradient(
                                        listOf(
                                            Color.Transparent,
                                            Color.Black
                                        )
                                    )
                                )
                                .padding(4.dp)
                                .align(BottomStart),
                            text = stringResource(R.string.likes_count).format(galleryItem.likes),
                            fontSize = 12.sp,
                        )
                    }
                }
            }
        }
    }
}