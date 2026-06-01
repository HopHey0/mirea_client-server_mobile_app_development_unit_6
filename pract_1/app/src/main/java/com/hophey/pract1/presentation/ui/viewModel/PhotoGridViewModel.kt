package com.hophey.pract1.presentation.ui.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hophey.pract1.domain.entity.Photo
import com.hophey.pract1.domain.useCase.GetPhotosListUseCase
import com.hophey.pract1.domain.useCase.SavePhotoUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.OutputStream


sealed class PhotoGridUiState{
    class Loading: PhotoGridUiState()
    class Error(val error: String): PhotoGridUiState()
    class Success(
        val photos: List<Photo>
    ): PhotoGridUiState()
}

data class DetailsSheetState(
    val showSheet: Boolean = false,
    val photo: Photo? = null,
    val isSaving: Boolean = false
)

class PhotoGridViewModel(
    private val getPhotosListUseCase: GetPhotosListUseCase,
    private val savePhotoUseCase: SavePhotoUseCase
) : ViewModel() {
    val _uiState = MutableStateFlow<PhotoGridUiState>(PhotoGridUiState.Loading())
    val uiState = _uiState.asStateFlow()

    val _sheetState = MutableStateFlow<DetailsSheetState>(DetailsSheetState())
    val sheetState = _sheetState.asStateFlow()

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

    fun onCardClick(photo: Photo){
        _sheetState.update {
            it.copy(
                showSheet = true,
                photo = photo
            )
        }
    }

    fun dismissSheet(){
        _sheetState.update {
            it.copy(
                showSheet = false,
                photo = null
            )
        }
    }

    fun savePhoto(downloadUrl: String, outputStream: OutputStream) {
        viewModelScope.launch {
            _sheetState.update { it.copy(isSaving = true) }
            savePhotoUseCase(downloadUrl, outputStream)
            _sheetState.update { it.copy(isSaving = false) }
        }
    }
}