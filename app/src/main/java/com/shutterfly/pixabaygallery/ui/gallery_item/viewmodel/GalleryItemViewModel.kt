package com.shutterfly.pixabaygallery.ui.gallery_item.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.shutterfly.pixabaygallery.core.repository.GalleryRepository
import com.shutterfly.pixabaygallery.models.network_models.GalleryItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class GalleryItemViewModel(
    private val galleryRepository: GalleryRepository = GalleryRepository()
) : ViewModel() {

    private val _isFavoriteItem = MutableStateFlow(false)
    val isFavoriteItem = _isFavoriteItem.asStateFlow()


    fun getIsFavoriteItem(item: GalleryItem) = viewModelScope.launch {
        _isFavoriteItem.value = galleryRepository.isFavoriteItem(item)
    }

    fun changeFavorite(item: GalleryItem) = viewModelScope.launch {
        _isFavoriteItem.value = galleryRepository.changeFavorite(item)
    }


    class GalleryItemViewModelFactory(private val repository: GalleryRepository = GalleryRepository()) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return if (modelClass.isAssignableFrom(GalleryItemViewModel::class.java)) {
                GalleryItemViewModel(repository) as T
            } else {
                throw IllegalArgumentException("ViewModel not found")
            }
        }
    }
}