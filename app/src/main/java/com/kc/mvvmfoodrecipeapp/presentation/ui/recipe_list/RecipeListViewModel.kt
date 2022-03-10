package com.kc.mvvmfoodrecipeapp.presentation.ui.recipe_list

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kc.mvvmfoodrecipeapp.data.model.RecipeItem
import com.kc.mvvmfoodrecipeapp.data.repository.RecipeRepository
import com.kc.mvvmfoodrecipeapp.data.util.Constants.Companion.ERROR_MESSAGE
import com.kc.mvvmfoodrecipeapp.data.util.Constants.Companion.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

const val PAGE_SIZE = 30
const val STATE_KEY_PAGE = "recipe.state.page.key"
const val STATE_KEY_QUERY = "recipe.state.query.key"
const val STATE_KEY_LIST_POSITION = "recipe.state.query.list_position"
const val STATE_KEY_SELECTED_CATEGORY = "recipe.state.query.selected_category"

@HiltViewModel
class RecipeListViewModel
@Inject constructor(
    private val repository: RecipeRepository,
    @Named("auth_token") private val token: String,
    private val savedStateHandle: SavedStateHandle
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
        savedStateHandle.get<Int>(STATE_KEY_PAGE)?.let { p ->
            setPage(p)
        }

        savedStateHandle.get<String>(STATE_KEY_QUERY)?.let { q ->
            setQuery(q)
        }

        savedStateHandle.get<Int>(STATE_KEY_LIST_POSITION)?.let { lp ->
            setListScrollPosition(lp)
        }

        savedStateHandle.get<FoodCategory>(STATE_KEY_SELECTED_CATEGORY)?.let { c ->
            setSelectedCategory(c)
        }

        if(recipeListScrollPosition != 0){
            onTriggerEvent(RecipeListEvent.RestoreStateEvent)
        } else{
            onTriggerEvent(RecipeListEvent.NewSearchEvent)
        }
    }

    fun onTriggerEvent(event: RecipeListEvent){
        viewModelScope.launch {
            try {
                when(event) {
                    is RecipeListEvent.NewSearchEvent -> {
                        newSearch()
                    }
                    is RecipeListEvent.NextPageEvent -> {
                        nextPage()
                    }
                    is RecipeListEvent.RestoreStateEvent -> {
                        restoreState()
                    }
                }
            }catch (e: Exception){
                Log.e(TAG, "launchJob: Exception: $e, ${e.cause}")
            }
        }
    }

    private suspend fun restoreState(){
        loading.value = true
        val results: MutableList<RecipeItem> = mutableListOf()
        for(p in 1..page.value){
            val result = repository.search(
                token = token,
                page = p,
                query = query.value
            )
            results.addAll(result)
            if(p == page.value){
                recipes = results
                loading.value = false
            }
        }
    }

    private suspend fun newSearch(){
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

    private suspend fun nextPage(){
        // prevent duplicate events due to recompose happening to quickly
        if((recipeListScrollPosition + 1) >= (page.value * PAGE_SIZE)){
            loading.value = true
            incrementPage()
            Log.d(TAG, "nextPage: triggered: ${page.value}")

            delay(1000)

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

    /**
     * Append new recipes to the current list of recipes
     */
    private fun appendRecipes(recipe: List<RecipeItem>){
        val currentList = ArrayList(this.recipes)
        currentList.addAll(recipe)
        this.recipes = currentList
    }

    private fun incrementPage(){
        setPage(page.value + 1)
    }

    fun onChangeRecipeScrollPosition(position: Int){
        setListScrollPosition(position = position)
    }

    private fun clearSelectedCategory(){
        setSelectedCategory(null)
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
        setQuery(query)
    }

    fun onSelectedCategoryChanged(category: String){
        val newCategory = getFoodCategory(category)
        setSelectedCategory(newCategory)
        onQueryChanged(category)
    }

    fun onChangeCategoryScrollPosition(position: Int){
        categoryScrollPosition = position
    }

    fun onToggleTheme(){
            isDark.value = !isDark.value
        }

    private fun setListScrollPosition(position: Int){
        recipeListScrollPosition = position
        savedStateHandle.set(STATE_KEY_LIST_POSITION, position)
    }

    private fun setPage(page: Int){
        this.page.value = page
        savedStateHandle.set(STATE_KEY_PAGE, page)
    }

    private fun setSelectedCategory(category: FoodCategory?){
        selectedCategory.value = category
        savedStateHandle.set(STATE_KEY_SELECTED_CATEGORY, category)
    }

    private fun setQuery(query: String){
        this.query.value = query
        savedStateHandle.set(STATE_KEY_QUERY, query)
    }
}

