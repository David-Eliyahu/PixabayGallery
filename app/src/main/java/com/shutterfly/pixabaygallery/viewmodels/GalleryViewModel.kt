package com.shutterfly.pixabaygallery.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.shutterfly.pixabaygallery.models.GalleryItem
import com.shutterfly.pixabaygallery.repositories.GalleryRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GalleryViewModel(
    private val repository: GalleryRepository
) : ViewModel() {

    private companion object {
        private const val DEFAULT_SEARCH_KEYWORD = "android"
    }

    private val _currentKeyword = MutableStateFlow(DEFAULT_SEARCH_KEYWORD)
    val currentKeyword = _currentKeyword.asStateFlow()

    var currentImage = MutableLiveData<GalleryItem?>(null)

    @OptIn(ExperimentalCoroutinesApi::class)
    val galleryItemsFlow: Flow<PagingData<GalleryItem>> =
        _currentKeyword.flatMapLatest { keyword ->
            repository.searchImages(keyword)
        }.cachedIn(viewModelScope)

    fun onSearchButtonClicked(keyword: String) {
        if (keyword.isNotBlank()) {
            _currentKeyword.update { keyword }
        }
    }

    fun toggleFavorite(id: String) {
        viewModelScope.launch {
            repository.toggleFavorite(id)
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