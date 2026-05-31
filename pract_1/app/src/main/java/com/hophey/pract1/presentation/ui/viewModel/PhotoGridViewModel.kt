package com.hophey.pract1.presentation.ui.viewModel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hophey.pract1.domain.entity.Photo
import com.hophey.pract1.domain.useCase.GetPhotosListUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


sealed class PhotoGridUiState{
    class Loading: PhotoGridUiState()
    class Error(val error: String): PhotoGridUiState()
    class Success(val photos: List<Photo>): PhotoGridUiState()
}

class PhotoGridViewModel(
    private val getPhotosListUseCase: GetPhotosListUseCase
) : ViewModel() {
    val _uiState = MutableStateFlow<PhotoGridUiState>(PhotoGridUiState.Loading())
    val uiState = _uiState.asStateFlow()

    init {
        loadPhotos()
    }

    fun loadPhotos() = viewModelScope.launch{
        _uiState.value = PhotoGridUiState.Loading()

        val result = getPhotosListUseCase.invoke()

        result
            .onSuccess { photos ->
                _uiState.value = PhotoGridUiState.Success(photos)
            }
            .onFailure { error ->
                Log.e("Network errors", error.cause.toString())
                _uiState.value = PhotoGridUiState.Error(error.message ?: "Unknown error")
            }
    }
}