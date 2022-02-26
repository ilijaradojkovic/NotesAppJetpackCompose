package com.example.mynoteapp.Data

import androidx.compose.ui.graphics.Color

enum class Priority( val color: Color) {

    HIGH(Color.Red),
    LOW(Color.Green),
    MEDIUM(Color.Yellow),
    NONE(Color.White)
}