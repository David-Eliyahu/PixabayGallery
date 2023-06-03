package com.shutterfly.pixabaygallery.ui.gallery_list.viewmodel

import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.shutterfly.pixabaygallery.repositories.GalleryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class GalleryViewModel(private val repository: GalleryRepository) : ViewModel() {

    private companion object {
        private const val DEFAULT_SEARCH_KEYWORD = "android"
    }

    private val _currentSearchTerm = MutableLiveData(DEFAULT_SEARCH_KEYWORD)
    val imageListObservable = _currentSearchTerm.switchMap { keyword ->
        repository.searchImages(keyword)
    }.cachedIn(viewModelScope)

    private val _searchTextFieldValue = MutableStateFlow("")
    val searchTextFieldValue = _searchTextFieldValue.asStateFlow()

    fun onSearchButtonClicked() = viewModelScope.launch {
        _currentSearchTerm.value = _searchTextFieldValue.value
    }

    fun onSearchTermChanged(searchTerm: String) = viewModelScope.launch {
        _searchTextFieldValue.value = searchTerm
    }
}

class GalleryViewModelFactory(private val repository: GalleryRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(GalleryViewModel::class.java)) {
            GalleryViewModel(repository) as T
        } else {
            throw IllegalArgumentException("ViewModel not found")
        }
    }
}