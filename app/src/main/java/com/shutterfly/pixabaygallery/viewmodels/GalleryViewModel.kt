package com.shutterfly.pixabaygallery.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asFlow
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.shutterfly.pixabaygallery.models.GalleryItem
import com.shutterfly.pixabaygallery.repositories.GalleryRepository

class GalleryViewModel(
    private val repository: GalleryRepository
) : ViewModel() {

    private companion object {
        private const val DEFAULT_SEARCH_KEYWORD = "android"
    }

    private val _currentKeyword = MutableLiveData(DEFAULT_SEARCH_KEYWORD)
    var currentImage = MutableLiveData<GalleryItem?>(null)

    val imageListPagingFlow = _currentKeyword.switchMap { keyword ->
        repository.searchImages(keyword)
    }.asFlow().cachedIn(viewModelScope)

    fun onSearchButtonClicked(keyword: String) {
        if (keyword.isNotBlank()) {
            _currentKeyword.value = keyword
        }
    }

    fun onImageClicked(
        galleryItem: GalleryItem
    ) {
        currentImage.value = galleryItem
    }
}

class GalleryViewModelFactory(
    private val repository: GalleryRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(GalleryViewModel::class.java)) {
            GalleryViewModel(repository) as T
        } else {
            throw IllegalArgumentException("ViewModel not found")
        }
    }
}