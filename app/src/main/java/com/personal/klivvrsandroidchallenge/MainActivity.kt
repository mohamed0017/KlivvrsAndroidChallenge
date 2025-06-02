package com.personal.klivvrsandroidchallenge

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.personal.klivvrsandroidchallenge.ui.theme.KlivvrsAndroidChallengeTheme
import dagger.hilt.android.AndroidEntryPoint
import androidx.activity.viewModels
import com.personal.klivvrsandroidchallenge.ui.screens.cities.CityListScreen
import androidx.core.net.toUri
import com.personal.klivvrsandroidchallenge.ui.screens.cities.CityViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: CityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
                            val uri =
                                "geo:${city.coordinates.latitude},${city.coordinates.longitude}?q=${city.name}".toUri()
                            val intent = Intent(Intent.ACTION_VIEW, uri)
                            startActivity(intent)
                        }
                    )
                }
            }
        }
    }
} 