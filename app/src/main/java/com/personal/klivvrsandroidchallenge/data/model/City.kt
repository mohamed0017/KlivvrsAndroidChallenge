package com.personal.klivvrsandroidchallenge.data.model

data class City(
    val id: Long,
    val name: String,
    val country: String,
    val coordinates: Coordinates
)

data class Coordinates(
    val latitude: Double,
    val longitude: Double
) 