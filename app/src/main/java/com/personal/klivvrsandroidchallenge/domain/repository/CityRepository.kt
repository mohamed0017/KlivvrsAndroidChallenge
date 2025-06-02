package com.personal.klivvrsandroidchallenge.domain.repository

import com.personal.klivvrsandroidchallenge.domain.model.CityDomain

interface CityRepository {
    suspend fun loadCities(): List<CityDomain>
    fun searchCities(prefix: String): List<CityDomain>
}