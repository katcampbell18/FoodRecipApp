package com.kc.mvvmfoodrecipeapp

sealed class Screen(val route: String) {
    object RecipeListScreen: Screen("recipeList_screen")
    object RecipeDetailScreen: Screen("recipeDetail_screen")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach {
                arg -> append("/$arg")
            }
        }
    }
}
