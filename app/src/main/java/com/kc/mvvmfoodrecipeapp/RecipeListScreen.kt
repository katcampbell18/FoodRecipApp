package com.kc.mvvmfoodrecipeapp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun RecipeListScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .padding(16.dp)
    ) {
        Text(
            text = "Recipe List",
            style = TextStyle(
                fontSize = 21.sp
            )
        )
        Spacer(
            modifier = Modifier
                .padding(10.dp)
        )
        Button(onClick = {
            navController.navigate(Screen.RecipeDetailScreen.route)
        }) {
            Text(text = "TO RECIPE FRAGMENT")
        }
    }
}