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
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.personal.klivvrsandroidchallenge.data.model.City
import com.personal.klivvrsandroidchallenge.ui.components.CityItem
import com.personal.klivvrsandroidchallenge.ui.viewmodel.CityViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun CityListScreen(
    viewModel: CityViewModel,
    onCityClick: (City) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val searchBarFocused = remember { mutableStateOf(false) }
    val searchBarWidth by animateDpAsState(
        if (searchBarFocused.value) 340.dp else 300.dp, label = "SearchBarWidth"
    )
    val searchBarColor by animateColorAsState(
        if (searchBarFocused.value) Color.White else Color(0xFFF5F0F5), label = "SearchBarColor"
    )
    val listState = rememberLazyListState()
    val groupedCities = uiState.cities.groupBy { it.name.first().uppercaseChar() }.toSortedMap()

    Box(Modifier.fillMaxSize().background(Color(0xFFF5F0F5))) {
        Column(Modifier.fillMaxSize()) {
            Text(
                "City Search",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 24.dp, start = 24.dp)
            )
            AnimatedVisibility(visible = !uiState.isLoading) {
                Text(
                    "${uiState.cities.size} cities",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(start = 24.dp, top = 8.dp, bottom = 8.dp)
                )
            }
            Box(Modifier.weight(1f)) {
                androidx.compose.animation.AnimatedVisibility(
                    visible = uiState.isLoading,
                    modifier = Modifier.align(Alignment.Center)
                ) {
                    CircularProgressIndicator()
                }
                androidx.compose.animation.AnimatedVisibility(
                    visible = !uiState.isLoading && uiState.cities.isEmpty(),
                    modifier = Modifier.align(Alignment.Center)
                ) {
                    Text("No cities found")
                }
                androidx.compose.animation.AnimatedVisibility(
                    visible = !uiState.isLoading && uiState.cities.isNotEmpty(),
                    modifier = Modifier.fillMaxSize()
                ) {
                    LazyColumn(
                        state = listState,
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(vertical = 8.dp)
                    ) {
                        var isFirst = true
                        groupedCities.forEach { (letter, cities) ->
                            stickyHeader {
                                GroupHeader(letter = letter, isFirst = isFirst, isLast = false)
                            }
                            itemsIndexed(cities) { idx, city ->
                                CityItem(
                                    city = city,
                                    onCityClick = onCityClick
                                )
                            }
                            isFirst = false
                        }
                    }
                }
            }
        }
        AnimatedSearchBar(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
                .width(searchBarWidth)
                .background(searchBarColor, shape = CircleShape),
            value = uiState.searchQuery,
            onValueChange = { viewModel.onSearchQueryChange(it) },
            onFocusChanged = { searchBarFocused.value = it }
        )
    }
}

@Composable
fun GroupHeader(letter: Char, isFirst: Boolean, isLast: Boolean) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        // Circle with letter
        Box(
            Modifier
                .size(40.dp)
                .background(Color.White, CircleShape)
                .clip(CircleShape)
                .border(2.dp, Color.LightGray, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(letter.toString(), style = MaterialTheme.typography.titleMedium)
        }
        // Vertical line
        Box(
            Modifier
                .width(2.dp)
                .height(60.dp)
                .background(Color.LightGray)
        )
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
        modifier = modifier
            .onFocusChanged { focusState ->
                isFocused = focusState.isFocused
                onFocusChanged(isFocused)
            },
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
} 