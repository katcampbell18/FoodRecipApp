package com.kc.mvvmfoodrecipeapp.data.repository

import android.util.Log
import com.kc.mvvmfoodrecipeapp.data.model.RecipeItem
import com.kc.mvvmfoodrecipeapp.data.network.RecipeDtoMapper
import com.kc.mvvmfoodrecipeapp.data.network.RecipeService
import com.kc.mvvmfoodrecipeapp.data.util.Constants.Companion.ERROR_MESSAGE
import com.kc.mvvmfoodrecipeapp.data.util.Constants.Companion.TAG

class RecipeRepository_Implementation(
    private val recipeService: RecipeService,
    private val mapper: RecipeDtoMapper
): RecipeRepository {
    override suspend fun search(token: String, page: Int, query: String): List<RecipeItem> {
        try{
        mapper.toDomainList(recipeService.search(token, page, query).recipes)
    } catch(e: Exception){
        Log.e(TAG, ERROR_MESSAGE)
        }
        return mapper.toDomainList(recipeService.search(token, page, query).recipes)
    }

    override suspend fun get(token: String, id: Int): RecipeItem {
        try{
            mapper.mapToDomainModel(recipeService.get(token, id))
        } catch(e: Exception){
            Log.e(TAG, ERROR_MESSAGE)
        }
        return mapper.mapToDomainModel(recipeService.get(token, id))
    }
}