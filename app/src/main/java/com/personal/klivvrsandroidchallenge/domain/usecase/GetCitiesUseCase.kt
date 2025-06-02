package com.personal.klivvrsandroidchallenge.domain.usecase

import com.personal.klivvrsandroidchallenge.domain.model.CityDomain
import com.personal.klivvrsandroidchallenge.domain.repository.CityRepository
import javax.inject.Inject

class GetCitiesUseCase @Inject constructor(
    private val repository: CityRepository
) {
    suspend operator fun invoke(): List<CityDomain> {
        return repository.loadCities()
    }
} 