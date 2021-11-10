package com.kc.mvvmfoodrecipeapp.di

import com.kc.mvvmfoodrecipeapp.data.network.RecipeDtoMapper
import com.kc.mvvmfoodrecipeapp.data.network.RecipeService
import com.kc.mvvmfoodrecipeapp.data.repository.RecipeRepository
import com.kc.mvvmfoodrecipeapp.data.repository.RecipeRepository_Implementation
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.internal.managers.ApplicationComponentManager
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponentManager::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideRecipeRepository(
        recipeService: RecipeService,
        recipeDtoMapper: RecipeDtoMapper
    ): RecipeRepository{
        return RecipeRepository_Implementation(recipeService, recipeDtoMapper)
    }
}