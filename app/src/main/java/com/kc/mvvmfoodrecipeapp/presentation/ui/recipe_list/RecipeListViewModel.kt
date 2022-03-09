package com.kc.mvvmfoodrecipeapp.presentation.ui.recipe_list

import android.media.MediaRouter
import android.util.Log
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kc.mvvmfoodrecipeapp.data.model.RecipeItem
import com.kc.mvvmfoodrecipeapp.data.repository.RecipeRepository
import com.kc.mvvmfoodrecipeapp.data.util.Constants.Companion.ERROR_MESSAGE
import com.kc.mvvmfoodrecipeapp.data.util.Constants.Companion.TAG
import com.kc.mvvmfoodrecipeapp.presentation.ui.components.util.PAGE_SIZE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class RecipeListViewModel
@Inject constructor(
        private val repository: RecipeRepository,
        private @Named("auth_token") val token: String
    ): ViewModel(){

    private var recipes: List<RecipeItem> by mutableStateOf(listOf())

    val query = mutableStateOf("")

    val selectedCategory: MutableState<FoodCategory?> = mutableStateOf(null)

    var categoryScrollPosition: Int = 0

    val loading = mutableStateOf(false)

    val isDark = mutableStateOf(false)

    val page = mutableStateOf(1)

    private var recipeListScrollPosition = 0

    init {
        newSearch()
    }

    fun newSearch(){
        viewModelScope.launch{
            try{
                loading.value = true
                resetSearchState()
                delay(3000)

                val result = repository.search(
                    token = token,
                    page = 1,
                    query = query.value)
                recipes = result
                loading.value = false
            } catch(e: Exception){
                Log.e(TAG, ERROR_MESSAGE)
            }
        }
    }

    fun nextPage(){
        viewModelScope.launch {
            // prevent duplicate events due to recompose happening to quickly
            if((recipeListScrollPosition + 1) >= (page.value * PAGE_SIZE)){
                loading.value = true
                incrementPage()
                Log.d(TAG, "nextPage: triggered: ${page.value}")

                if(page.value > 1){
                    val result = repository.search(
                        token = token,
                        page = page.value,
                        query = query.value
                    )
                    Log.d(TAG, "search: appending: $result")
                    appendRecipes(result)
                }
                loading.value = false
            }
        }
    }

    /**
     * Append new recipes to the current list of recipes
     */
    private fun appendRecipes(recipe: List<RecipeItem>){
        val currentList = ArrayList(this.recipes)
        currentList.addAll(recipe)
        this.recipes = currentList
    }

    private fun incrementPage(){
        page.value = page.value + 1
    }

    fun onChangeRecipeScrollPosition(position: Int){
        recipeListScrollPosition = position
    }

    private fun clearSelectedCategory(){
        selectedCategory.value = null
    }

    /**
     * Called when a new search is executed
     */
    private fun resetSearchState(){
        recipes = listOf()
        page.value = 1
        onChangeRecipeScrollPosition(0)
        if(selectedCategory.value?.value != query.value){
            clearSelectedCategory()
        }
    }

    fun onQueryChanged(query: String){
        this.query.value = query
    }

    fun onSelectedCategoryChanged(category: String){
        val newCategory = getFoodCategory(category)
        selectedCategory.value = newCategory
    }

    fun onChangeCategoryScrollPosition(position: Int){
        categoryScrollPosition = position
    }

    fun onToggleTheme(){
            isDark.value = !isDark.value
        }
    }

