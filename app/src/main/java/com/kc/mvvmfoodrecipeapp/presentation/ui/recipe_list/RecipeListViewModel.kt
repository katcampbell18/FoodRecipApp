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

    private fun clearSelectedCategory(){
        selectedCategory.value = null
    }

    private fun resetSearchState(){
        recipes = listOf()
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

