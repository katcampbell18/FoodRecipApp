package com.kc.mvvmfoodrecipeapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kc.mvvmfoodrecipeapp.data.model.RecipeItem
import com.kc.mvvmfoodrecipeapp.presentation.ui.RecipeCard
import com.kc.mvvmfoodrecipeapp.presentation.ui.SearchAppBar
import com.kc.mvvmfoodrecipeapp.presentation.ui.components.CircularIndeterminateProgressBar
import com.kc.mvvmfoodrecipeapp.presentation.ui.components.ShimmerRecipeCardItem
import com.kc.mvvmfoodrecipeapp.presentation.ui.recipe_list.RecipeListViewModel


@Composable
fun RecipeListScreen(recipeList: List<RecipeItem>, vm: RecipeListViewModel) {

    val query = vm.query.value
    val selectedCategory = vm.selectedCategory.value
    val categoryScrollPosition = vm.categoryScrollPosition
    val loading = vm.loading.value

    val listState = rememberLazyListState()

    Column{
        SearchAppBar(
            query = query,
            onQueryChanged = vm::onQueryChanged,
            onExecuteSearch = vm::newSearch,
            selectedCategory = selectedCategory,
            onSelectedCategoryChanged = vm::onSelectedCategoryChanged,
            categoryScrollPosition = categoryScrollPosition,
            onChangeCategoryScrollPosition = vm::onChangeCategoryScrollPosition,
            onToggleTheme = vm::onToggleTheme,
        )

        Box (
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colors.background)
                ) {
            if (loading) {
                ShimmerRecipeCardItem(
                    imageHeight = 250.dp,
                    padding = 8.dp
                )
            } else {
                LazyColumn(state = listState) {
                    itemsIndexed(
                        items = recipeList
                    )
                    { index, recipe ->
                        RecipeCard(recipe = recipe, onClick = { onClick })
                    }
                }
                CircularIndeterminateProgressBar(
                    isDisplayed = loading
                )
            }
        }
    }
}