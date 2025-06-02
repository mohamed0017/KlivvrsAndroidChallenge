package com.personal.klivvrsandroidchallenge.ui.screens.cities

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign.Companion.Center
import androidx.compose.ui.unit.dp
import com.personal.klivvrsandroidchallenge.domain.model.CityDomain
import com.personal.klivvrsandroidchallenge.ui.screens.cities.components.AnimatedSearchBar
import com.personal.klivvrsandroidchallenge.ui.screens.cities.components.CityItem
import com.personal.klivvrsandroidchallenge.ui.screens.cities.components.GroupHeader

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CityListScreen(
    viewModel: CityViewModel,
    onCityClick: (CityDomain) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val searchBarFocused = remember { mutableStateOf(false) }

    val searchBarWidth by animateDpAsState(
        targetValue = if (searchBarFocused.value) 340.dp else 300.dp,
        label = "SearchBarWidth"
    )
    val searchBarColor by animateColorAsState(
        targetValue = if (searchBarFocused.value) Color.White else Color(0xFFF5F0F5),
        label = "SearchBarColor"
    )

    val listState = rememberLazyListState()

    Box(
        Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F0F5))
    ) {
        Column(Modifier.fillMaxSize()) {
            Text(
                "City Search",
                style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(top = 24.dp, start = 24.dp)
            )

            AnimatedVisibility(visible = !uiState.isLoading) {
                Text(
                    "${uiState.groupedCities.values.flatten().size} cities",
                    style = MaterialTheme.typography.bodyMedium.copy(textAlign = Center),
                    modifier = Modifier.fillMaxWidth().padding(start = 24.dp, top = 24.dp, bottom = 12.dp),
                    color = Color.DarkGray,
                )
            }

            Box(Modifier.fillMaxWidth().weight(1f)) {
                when {
                    uiState.isLoading -> {
                        CircularProgressIndicator(Modifier.align(Alignment.Center))
                    }

                    uiState.groupedCities.isEmpty() -> {
                        Text("No cities found", Modifier.align(Alignment.Center))
                    }

                    else -> {
                        LazyColumn(
                            state = listState,
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(bottom = 100.dp)
                        ) {
                            var isFirst = true
                            val entries = uiState.groupedCities.entries.toList()
                            entries.forEachIndexed { index, (letter, cities) ->
                                val isLast = index == entries.size - 1

                                stickyHeader {
                                    GroupHeader(letter = letter, isFirst = isFirst, isLast = isLast)
                                }

                                itemsIndexed(cities) { idx, city ->
                                    CityItem(
                                        city = city,
                                        onCityClick = onCityClick,
                                    )
                                }

                                isFirst = false
                            }
                        }
                    }
                }
            }
        }

        AnimatedSearchBar(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 24.dp, start = 16.dp, end = 16.dp)
                .width(searchBarWidth)
                .shadow(elevation = 4.dp, shape = CircleShape)
                .background(searchBarColor, shape = CircleShape),
            value = uiState.searchQuery,
            onValueChange = viewModel::onSearchQueryChange,
            onFocusChanged = { searchBarFocused.value = it }
        )
    }
}