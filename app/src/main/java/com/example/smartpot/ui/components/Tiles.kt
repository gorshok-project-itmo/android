package com.example.smartpot.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class Tile(val title: String, val subtitle: String)

@Composable
fun Tiles(tiles: List<Tile>) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        for (tile in tiles) {
            Box(
                modifier = Modifier.background(Color.DarkGray).fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = tile.title,
                        fontSize = 14.sp
                    )

                    Text(
                        text = tile.subtitle,
                        fontSize = 22.sp
                    )
                }
            }
        }
    }
}