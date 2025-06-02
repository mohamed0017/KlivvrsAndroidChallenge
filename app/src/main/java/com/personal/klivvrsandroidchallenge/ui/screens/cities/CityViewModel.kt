package com.personal.klivvrsandroidchallenge.ui.screens.cities

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.personal.klivvrsandroidchallenge.domain.model.CityDomain
import com.personal.klivvrsandroidchallenge.domain.usecase.GetCitiesUseCase
import com.personal.klivvrsandroidchallenge.domain.usecase.SearchCitiesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CityUiState(
    val groupedCities: Map<Char, List<CityDomain>> = emptyMap(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val searchQuery: String = ""
)

@HiltViewModel
class CityViewModel @Inject constructor(
    private val getCitiesUseCase: GetCitiesUseCase,
    private val searchCitiesUseCase: SearchCitiesUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(CityUiState())
    val uiState: StateFlow<CityUiState> = _uiState.asStateFlow()

    init {
        loadCities()
    }

    private fun loadCities() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                val cities =  getCitiesUseCase()
                _uiState.value = _uiState.value.copy(
                    groupedCities = cities.sortedBy { it.name } // <-- sort cities first
                        .groupBy { it.name.first().uppercaseChar() }
                        .toSortedMap(),
                    isLoading = false
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = e.message,
                    isLoading = false
                )
            }
        }
    }

    fun onSearchQueryChange(query: String) {
        _uiState.value = _uiState.value.copy(
            searchQuery = query,
            groupedCities = searchCitiesUseCase(query)
                .sortedBy { it.name } // <-- sort cities first
                .groupBy { it.name.first().uppercaseChar() }.toSortedMap()
        )
    }


} 