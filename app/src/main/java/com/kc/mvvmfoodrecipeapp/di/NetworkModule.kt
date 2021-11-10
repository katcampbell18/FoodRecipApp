package com.kc.mvvmfoodrecipeapp.di

import com.google.gson.GsonBuilder
import com.kc.mvvmfoodrecipeapp.data.network.RecipeDtoMapper
import com.kc.mvvmfoodrecipeapp.data.network.RecipeService
import com.kc.mvvmfoodrecipeapp.data.util.Constants.Companion.AUTH_TOKEN
import com.kc.mvvmfoodrecipeapp.data.util.Constants.Companion.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.internal.managers.ApplicationComponentManager
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponentManager:: class)
object NetworkModule {

    @Singleton
    @Provides
    fun providesRecipeMapper(): RecipeDtoMapper{
        return RecipeDtoMapper()
    }

    @Singleton
    @Provides
    fun provideRecipeService(): RecipeService{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(RecipeService::class.java)
    }

    @Singleton
    @Provides
    @Named("auth_token")
    fun provideAuthToken(): String{
        return AUTH_TOKEN
    }
}