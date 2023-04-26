package com.shutterfly.pixabaygallery.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.shutterfly.pixabaygallery.datastore.FavoritesLocalDataSource
import com.shutterfly.pixabaygallery.repositories.GalleryRepository
import com.shutterfly.pixabaygallery.viewmodels.GalleryViewModel
import com.shutterfly.pixabaygallery.viewmodels.GalleryViewModelFactory

private const val FAVORITES_FILE_NAME = "favorites_file"

class GalleryActivity : AppCompatActivity() {

    private val dataStore: DataStore<Preferences> by lazy {
        PreferenceDataStoreFactory.create(
            corruptionHandler = ReplaceFileCorruptionHandler(
                produceNewData = { emptyPreferences() }
            ),
            produceFile = {
                preferencesDataStoreFile(FAVORITES_FILE_NAME)
            }
        )
    }
    private val favoriteDataStore by lazy {
        FavoritesLocalDataSource(dataStore)
    }
    private val galleryRepository by lazy {
        GalleryRepository(favoriteDataStore, lifecycleScope)
    }
    private val viewModel by viewModels<GalleryViewModel> {
        GalleryViewModelFactory(galleryRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.GalleryScreen.route
                    ) {
                        composable(route = Screen.GalleryScreen.route) {
                            val galleryImages =
                                viewModel.galleryItemsFlow.collectAsLazyPagingItems()
                            val keyword = viewModel.currentKeyword.collectAsState()
                            GalleryScreen(
                                keyword = keyword.value,
                                galleryItems = galleryImages,
                                onSearch = {
                                    viewModel.onSearchButtonClicked(it)
                                },
                                navigateToImage = {
                                    viewModel.onImageClicked(it)
                                    navController.navigate(Screen.FullImageScreen.route)
                                },
                                onItemFavoriteClick = {
                                    viewModel.toggleFavorite(it)
                                }
                            )
                        }
                        composable(
                            route = Screen.FullImageScreen.route
                        ) {
                            val previewUrl = viewModel.currentImage.value?.previewUrl.orEmpty()
                            FullImageScreen(
                                previewUrl = previewUrl,
                                navigateBack = {
                                    navController.navigateUp()
                                }
                            )
                        }
                    }
                }
            }
        }
    }

    sealed class Screen(open val route: String) {
        object GalleryScreen : Screen("gallery")
        object FullImageScreen : Screen("full_image")
    }
}