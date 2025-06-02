# Klivvrs Android Challenge

A clean architecture Android application that displays and searches through a list of cities.

## Architecture

The project follows Clean Architecture principles with the following layers:

### Domain Layer
- **Models**: Core business entities (`CityDomain`, `Coordinates`)
- **Repository**: Interface defining data operations (`CityRepository`)
- **Use Cases**: Business logic implementation
  - `GetCitiesUseCase`: Handles retrieving cities
  - `SearchCitiesUseCase`: Handles city search operations
- **Exceptions**: Domain-specific error handling (`CityException`)

### Data Layer
- **Repository Implementation**: `CityRepositoryImpl` that implements the domain repository interface
- **Data Sources**: JSON file containing city data
- **Models**: Data layer models

### Presentation Layer
- **UI Components**: Jetpack Compose screens and components
  - `CityListScreen`: Main screen displaying the list of cities
  - `CityItem`: Individual city item component
  - `AnimatedSearchBar`: Search input component
  - `GroupHeader`: Section header component
- **ViewModel**: `CityViewModel` handling UI state and business logic
- **State**: `CityUiState` managing UI state

## Features

- Display list of cities grouped alphabetically
- Real-time city search with prefix matching
- Animated search bar
- City details including:
  - City name
  - Country
  - Coordinates
  - Country flag emoji
- Open city location in Google Maps
- Efficient search using prefix indexing
- Clean Architecture implementation
- Dependency Injection using Hilt
- Jetpack Compose UI
- Kotlin Coroutines for asynchronous operations

## Technical Stack

- **Language**: Kotlin
- **Architecture**: Clean Architecture
- **UI**: Jetpack Compose
- **Dependency Injection**: Hilt
- **Asynchronous**: Kotlin Coroutines
- **State Management**: StateFlow
- **Build System**: Gradle

## Project Structure

```
app/src/main/java/com/personal/klivvrsandroidchallenge/
├── data/
│   └── repository/
│       └── CityRepositoryImpl.kt
├── domain/
│   ├── model/
│   │   ├── CityDomain.kt
│   │   └── Coordinates.kt
│   ├── repository/
│   │   └── CityRepository.kt
│   ├── usecase/
│   │   ├── GetCitiesUseCase.kt
│   │   └── SearchCitiesUseCase.kt
│   └── exception/
│       └── CityException.kt
├── ui/
│   ├── screens/
│   │   └── cities/
│   │       ├── CityListScreen.kt
│   │       ├── CityViewModel.kt
│   │       └── components/
│   │           ├── CityItem.kt
│   │           ├── AnimatedSearchBar.kt
│   │           └── GroupHeader.kt
│   └── theme/
│       └── Theme.kt
└── di/
    └── RepositoryModule.kt
```

## Getting Started

1. Clone the repository
2. Open the project in Android Studio
3. Build and run the application

## Requirements

- Android Studio Arctic Fox or newer
- Android SDK 21 or higher
- Kotlin 1.8 or higher
- Gradle 7.0 or higher 