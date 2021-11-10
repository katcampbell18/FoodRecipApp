package com.kc.mvvmfoodrecipeapp

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RecipeDetailScreen() {
    Column(
        modifier = Modifier
            .border(border = BorderStroke(1.dp, Color.Black))
            .padding(16.dp)
    ) {
        Text(
            text = "RECIPE FRAGMENT",
            style = TextStyle(
                fontSize = 21.sp
            )
        )
        Spacer(modifier = Modifier
            .padding(10.dp))
    }
}