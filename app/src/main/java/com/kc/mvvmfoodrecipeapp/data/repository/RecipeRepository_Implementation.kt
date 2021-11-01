package com.kc.mvvmfoodrecipeapp.data.repository

import com.kc.mvvmfoodrecipeapp.data.model.RecipeItem
import com.kc.mvvmfoodrecipeapp.data.network.RecipeDtoMapper
import com.kc.mvvmfoodrecipeapp.data.network.RecipeService

class RecipeRepository_Implementation(
    private val recipeService: RecipeService,
    private val mapper: RecipeDtoMapper
): RecipeRepository {
    override suspend fun search(token: String, page: Int, query: String): List<RecipeItem> {
        return mapper.toDomainList(recipeService.search(token, page, query).recipes)
    }

    override suspend fun get(token: String, id: Int): RecipeItem {
        return mapper.mapToDomainModel(recipeService.get(token, id))
    }
}