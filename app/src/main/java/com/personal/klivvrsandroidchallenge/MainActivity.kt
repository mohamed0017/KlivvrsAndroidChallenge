package com.personal.klivvrsandroidchallenge

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.personal.klivvrsandroidchallenge.data.repository.CityRepository
import com.personal.klivvrsandroidchallenge.data.repository.CityRepositoryImpl
import com.personal.klivvrsandroidchallenge.ui.screens.CityListScreen
import com.personal.klivvrsandroidchallenge.ui.theme.KlivvrsAndroidChallengeTheme
import com.personal.klivvrsandroidchallenge.ui.viewmodel.CityViewModel

class MainActivity : ComponentActivity() {
    private lateinit var viewModel: CityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize dependencies
        val repository: CityRepository = CityRepositoryImpl(applicationContext)
        viewModel = CityViewModel(repository)

        setContent {
            KlivvrsAndroidChallengeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CityListScreen(
                        viewModel = viewModel,
                        onCityClick = { city ->
                            // Open Google Maps with the city coordinates
                            val uri = Uri.parse(
                                "geo:${city.coordinates.latitude},${city.coordinates.longitude}?q=${city.name}"
                            )
                            val intent = Intent(Intent.ACTION_VIEW, uri)
                            startActivity(intent)
                        }
                    )
                }
            }
        }
    }
} 