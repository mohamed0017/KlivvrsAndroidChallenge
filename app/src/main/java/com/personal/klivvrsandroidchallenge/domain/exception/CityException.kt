package com.personal.klivvrsandroidchallenge.domain.exception

sealed class CityException : Exception() {
    data class CityNotFound(val cityId: Long) : CityException() {
        override val message: String
            get() = "City with ID $cityId not found"
    }
    
    data class InvalidSearchQuery(val errorMessage: String) : CityException() {
        override val message: String
            get() = "Invalid search query: $errorMessage"
    }
    
    data class DataLoadError(val errorMessage: String) : CityException() {
        override val message: String
            get() = "Failed to load city data: $errorMessage"
    }
    
    data class NetworkError(val errorMessage: String) : CityException() {
        override val message: String
            get() = "Network error: $errorMessage"
    }
    
    data class UnknownError(val errorMessage: String) : CityException() {
        override val message: String
            get() = "Unknown error occurred: $errorMessage"
    }
} 