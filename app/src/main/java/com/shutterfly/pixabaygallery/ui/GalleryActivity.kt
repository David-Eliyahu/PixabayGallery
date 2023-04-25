package com.shutterfly.pixabaygallery.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.shutterfly.pixabaygallery.repositories.GalleryRepository
import com.shutterfly.pixabaygallery.ui.home.GalleryScreen
import com.shutterfly.pixabaygallery.viewmodels.GalleryViewModel
import com.shutterfly.pixabaygallery.viewmodels.GalleryViewModelFactory

class GalleryActivity : AppCompatActivity() {

    private val viewModel by viewModels<GalleryViewModel> {
        GalleryViewModelFactory(GalleryRepository())
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
                                viewModel.imageListPagingFlow.collectAsLazyPagingItems()
                            GalleryScreen(
                                galleryItems = galleryImages,
                                onSearch = { keyword ->
                                    viewModel.onSearchButtonClicked(keyword)
                                },
                                navigateToImage = {
                                    navController.navigate(Screen.FullImageScreen.route)
                                }
                            )
                        }
                        composable(route = Screen.FullImageScreen.route) {

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