package com.hophey.pract2.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hophey.pract2.domain.entity.NobelPrize
import com.hophey.pract2.domain.useCase.GetNobelPrizesByYearAndCategoryUseCase
import com.hophey.pract2.utils.getListOfYears
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed class NobelPrizesUiState {
    class Loading : NobelPrizesUiState()
    class Success(val nobelPrizes: List<NobelPrize>) : NobelPrizesUiState()
    class Error(val error: String) : NobelPrizesUiState()
}

data class NobelPrizeDetailsState(
    val showSheet: Boolean = false,
    val nobelPrize: NobelPrize? = null
)

data class FilterRowState(
    val yearListExpanded: Boolean = false,
    val chosenYear: String = "2013",
    val yearsList: List<String> = getListOfYears(),
    val isLoading: Boolean = false
)

class NobelPrizesViewModel(
    private val getNobelPrizesByYearAndCategoryUseCase: GetNobelPrizesByYearAndCategoryUseCase
) : ViewModel() {
    val _nobelPrizesState = MutableStateFlow<NobelPrizesUiState>(NobelPrizesUiState.Loading())
    val nobelPrizesState = _nobelPrizesState.asStateFlow()

    val _laureateDetailsState = MutableStateFlow<NobelPrizeDetailsState>(NobelPrizeDetailsState())
    val laureatesDetailsUiState = _laureateDetailsState.asStateFlow()

    val _filterRowState = MutableStateFlow<FilterRowState>(FilterRowState())
    val filterRowState = _filterRowState.asStateFlow()

    init {
        findNobelPrizes("2013")
    }

    fun findNobelPrizes(year: String) = viewModelScope.launch {
        _nobelPrizesState.value = NobelPrizesUiState.Loading()
        _filterRowState.update { it.copy(isLoading = true) }

        getNobelPrizesByYearAndCategoryUseCase(year)
            .onSuccess { success ->
                _nobelPrizesState.value = NobelPrizesUiState.Success(success)
                _filterRowState.update { it.copy(isLoading = false) }
            }
            .onFailure { error ->
                _nobelPrizesState.value = NobelPrizesUiState.Error(
                    error.message ?: "Something went wrong\nTry again later"
                )
                _filterRowState.update { it.copy(isLoading = false) }
            }
    }

    fun onNobelPrizeClicked(nobelPrize: NobelPrize) {
        _laureateDetailsState.update {
            it.copy(showSheet = true, nobelPrize = nobelPrize)
        }
    }

    fun onNobelPrizeDismiss() {
        _laureateDetailsState.update {
            it.copy(showSheet = false, nobelPrize = null)
        }
    }

    fun changeYearFilter(newYear: String) {
        _filterRowState.update {
            it.copy(chosenYear = newYear, yearListExpanded = false)
        }
    }

    fun onYearListDismiss() {
        _filterRowState.update { it.copy(yearListExpanded = false) }
    }

    fun onYearListExpand() {
        _filterRowState.update { it.copy(yearListExpanded = true) }
    }
}