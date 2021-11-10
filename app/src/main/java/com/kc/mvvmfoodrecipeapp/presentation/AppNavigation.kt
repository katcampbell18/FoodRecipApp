package com.kc.mvvmfoodrecipeapp

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kc.mvvmfoodrecipeapp.presentation.ui.Screen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.RecipeListScreen.route) {
        composable(route = Screen.RecipeListScreen.route) {
            RecipeListScreen(navController = navController)
        }
        composable(route = Screen.RecipeDetailScreen.route) {
            RecipeDetailScreen()
        }
    }
}
