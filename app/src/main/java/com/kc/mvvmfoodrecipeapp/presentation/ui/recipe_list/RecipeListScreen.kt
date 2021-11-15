package com.kc.mvvmfoodrecipeapp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import com.kc.mvvmfoodrecipeapp.data.model.RecipeItem
import com.kc.mvvmfoodrecipeapp.presentation.ui.HeaderScreen
import com.kc.mvvmfoodrecipeapp.presentation.ui.RecipeCard
import com.kc.mvvmfoodrecipeapp.presentation.ui.SearchAppBar
import com.kc.mvvmfoodrecipeapp.presentation.ui.recipe_list.RecipeListViewModel

@Composable
fun RecipeListScreen(recipeList: List<RecipeItem>, vm: RecipeListViewModel) {

    val query = vm.query.value
    val selectedCategory = vm.selectedCategory.value
    val categoryScrollPosition = vm.categoryScrollPosition

    val listState = rememberLazyListState()

    Column{
        SearchAppBar(
            query = query,
            onQueryChanged = vm::onQueryChanged,
            onExecuteSearch = vm::newSearch,
            selectedCategory = selectedCategory,
            onSelectedCategoryChanged = vm::onSelectedCategoryChanged,
            categoryScrollPosition = categoryScrollPosition,
            onChangeCategoryScrollPosition = vm::onChangeCategoryScrollPosition
        )
        LazyColumn(state = listState){
            itemsIndexed(
                items = recipeList)
            {index, recipe ->
                RecipeCard(recipe = recipe, onClick = {onClick} )
            }
        }
    }

}