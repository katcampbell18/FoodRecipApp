package com.kc.mvvmfoodrecipeapp.data.model

data class RecipeItem(
    val id: Int,
    val title: String,
    val publisher: String,
    val featuredImage: String,
    val rating: Int = 0,
    val sourceUrl: String,
    val description: String,
    val cookingInstructions: String,
    val ingredients: List<String> = listOf(),
    val dateAdded: String,
    val dateUpdated: String
)
