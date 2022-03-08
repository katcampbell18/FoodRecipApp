package com.kc.mvvmfoodrecipeapp

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.kc.mvvmfoodrecipeapp.presentation.ui.Screen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.RecipeListScreen.route) {
        composable(route = Screen.RecipeListScreen.route) {
            //TODO: fix navigation
            RecipeListScreen(navController = navController)
        }
        composable(route = Screen.RecipeDetailScreen.route + "/{id}",
            arguments = listOf(navArgument(name = "recipeID") {
                type = NavType.IntType
            })
            ) { entry ->
            RecipeDetailScreen(navController, entry.arguments?.getInt("recipeId"))
        }
    }
}
