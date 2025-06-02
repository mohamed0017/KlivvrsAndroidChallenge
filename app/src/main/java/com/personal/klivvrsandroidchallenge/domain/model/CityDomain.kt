package com.personal.klivvrsandroidchallenge.domain.model

data class CityDomain(
    val id: Long,
    val name: String,
    val country: String,
    val coordinates: Coordinates
)

data class Coordinates(
    val latitude: Double,
    val longitude: Double
) 