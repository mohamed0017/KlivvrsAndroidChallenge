package com.personal.klivvrsandroidchallenge.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.personal.klivvrsandroidchallenge.data.model.City
import com.personal.klivvrsandroidchallenge.ui.components.CityItem
import com.personal.klivvrsandroidchallenge.ui.viewmodel.CityViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CityListScreen(
    viewModel: CityViewModel,
    onCityClick: (City) -> Unit
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
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(start = 24.dp, top = 8.dp, bottom = 12.dp),
                    color = Color.Gray
                )
            }

            Box(Modifier.weight(1f)) {
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
                                        showVerticalLine = idx != cities.lastIndex
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


@Composable
fun GroupHeader(letter: Char, isFirst: Boolean, isLast: Boolean) {
    Row(
        verticalAlignment = Alignment.Top,
        modifier = Modifier
            .padding(start = 24.dp, top = if (isFirst) 16.dp else 24.dp, bottom = 8.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                Modifier
                    .size(36.dp)
                    .background(Color.White, CircleShape)
                    .border(1.dp, Color.LightGray, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = letter.toString(),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Medium
                )
            }
            if (!isLast) {
                Spacer(modifier = Modifier.height(4.dp))
                Box(
                    Modifier
                        .width(2.dp)
                    //    .height(800.dp)
                        .background(Color.LightGray)
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimatedSearchBar(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    onFocusChanged: (Boolean) -> Unit
) {
    var isFocused by remember { mutableStateOf(false) }

    TextField(
        value = value,
        onValueChange = onValueChange,
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
        placeholder = { Text("Search...") },
        singleLine = true,
        shape = CircleShape,
        modifier = modifier
            .onFocusChanged { focusState ->
                isFocused = focusState.isFocused
                onFocusChanged(isFocused)
            }
            .padding(horizontal = 8.dp),
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        )
    )
}
