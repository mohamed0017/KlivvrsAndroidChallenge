package com.personal.klivvrsandroidchallenge.ui.screens.cities.components

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.personal.klivvrsandroidchallenge.domain.model.CityDomain

@SuppressLint("DefaultLocale")
@Composable
fun CityItem(
    city: CityDomain,
    onCityClick: (CityDomain) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth()

    ) {
            Box(
                Modifier
                    .padding(start = 42.dp)
                    .width(2.dp)
                    .height(100.dp)
                    .background(Color.LightGray)
            )

        Card(
            modifier = Modifier
                .weight(1f) // Take remaining space
                .padding(start = 24.dp, end = 16.dp, top = 8.dp, bottom = 8.dp)
                .clickable { onCityClick(city) },
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Flag
                Text(
                    text = city.country.toFlagEmoji(),
                    fontSize = 24.sp
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "${city.name}, ${city.country}",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = String.format(
                            "%.5f, %.5f",
                            city.coordinates.longitude,
                            city.coordinates.latitude
                        ),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

fun String.toFlagEmoji(): String {
    return this
        .uppercase()
        .map { char -> 0x1F1E6 + (char.code - 'A'.code) }
        .joinToString("") { codePoint -> String(Character.toChars(codePoint)) }
}
