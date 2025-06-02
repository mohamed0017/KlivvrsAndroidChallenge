package com.personal.klivvrsandroidchallenge.domain.usecase

import com.personal.klivvrsandroidchallenge.domain.model.CityDomain
import com.personal.klivvrsandroidchallenge.domain.repository.CityRepository
import javax.inject.Inject

class SearchCitiesUseCase @Inject constructor(
    private val repository: CityRepository
) {
    operator fun invoke(query: String): List<CityDomain> {
        return repository.searchCities(query)
    }
} 