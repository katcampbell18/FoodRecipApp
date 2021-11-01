package com.kc.mvvmfoodrecipeapp.data.repository

import com.kc.mvvmfoodrecipeapp.data.model.RecipeItem

interface RecipeRepository {

    suspend fun search(token: String, page: Int, query: String): List<RecipeItem>

    suspend fun get(token: String, id: Int): RecipeItem
}