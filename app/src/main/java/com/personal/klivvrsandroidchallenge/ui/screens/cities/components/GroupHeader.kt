package com.personal.klivvrsandroidchallenge.ui.screens.cities.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

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
