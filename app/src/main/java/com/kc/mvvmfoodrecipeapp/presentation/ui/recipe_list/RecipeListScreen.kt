package com.kc.mvvmfoodrecipeapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.kc.mvvmfoodrecipeapp.data.model.RecipeItem
import com.kc.mvvmfoodrecipeapp.presentation.ui.RecipeCard
import com.kc.mvvmfoodrecipeapp.presentation.ui.Screen
import com.kc.mvvmfoodrecipeapp.presentation.ui.SearchAppBar
import com.kc.mvvmfoodrecipeapp.presentation.ui.components.CircularIndeterminateProgressBar
import com.kc.mvvmfoodrecipeapp.presentation.ui.components.DefaultSnackbar
import com.kc.mvvmfoodrecipeapp.presentation.ui.components.ShimmerRecipeCardItem
import com.kc.mvvmfoodrecipeapp.presentation.ui.components.scaffold.MyBottomBar
import com.kc.mvvmfoodrecipeapp.presentation.ui.components.scaffold.MyDrawer
import com.kc.mvvmfoodrecipeapp.presentation.ui.components.util.PAGE_SIZE
import com.kc.mvvmfoodrecipeapp.presentation.ui.components.util.SnackbarController
import com.kc.mvvmfoodrecipeapp.presentation.ui.recipe_list.FoodCategory
import com.kc.mvvmfoodrecipeapp.presentation.ui.recipe_list.RecipeListViewModel
import kotlinx.coroutines.launch


@Composable
fun RecipeListScreen(navController: NavController, recipeList: List<RecipeItem>, vm: RecipeListViewModel) {

    val query = vm.query.value
    val selectedCategory = vm.selectedCategory.value
    val categoryScrollPosition = vm.categoryScrollPosition
    val loading = vm.loading.value

    val listState = rememberLazyListState()

    val scaffoldState = rememberScaffoldState()

    val coroutineScope = rememberCoroutineScope()

    val snackbarController = SnackbarController(coroutineScope)

    val page = vm.page.value

    Scaffold(
        topBar = {
            SearchAppBar(
                query = query,
                onQueryChanged = vm::onQueryChanged,
                onExecuteSearch = {
                    if (selectedCategory != null) {
                        if (vm.selectedCategory.value == FoodCategory.valueOf(value = "Milk")) {
                            snackbarController.getScope().launch {
                                snackbarController.showSnackbar(
                                    scaffoldState = scaffoldState,
                                    message = "Invalid Category!",
                                    actionLabel = "Hide",
                                )
                            }
                        } else {
                            vm::newSearch
                        }
                    }
                },
                    selectedCategory = selectedCategory,
                    onSelectedCategoryChanged = vm::onSelectedCategoryChanged,
                    categoryScrollPosition = categoryScrollPosition,
                    onChangeCategoryScrollPosition = vm::onChangeCategoryScrollPosition,
                    onToggleTheme = vm::onToggleTheme,
                    )
        },
        scaffoldState = scaffoldState,
        snackbarHost = {
            scaffoldState.snackbarHostState
        },
        bottomBar = {
            MyBottomBar()
        },
        drawerContent = {
            MyDrawer()
        }
    ) {
        Box (
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colors.background)
                ) {
            if (loading && recipeList.isEmpty()) {
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
                        vm.onChangeRecipeScrollPosition(index)
                        if((index + 1) >= (page * PAGE_SIZE) && !loading){
                            vm.nextPage()
                        }
                        RecipeCard( recipe = recipe, onClick = {
                            navController.navigate(Screen.RecipeDetailScreen.route + "/${recipe.id}")},
                        )
                    }
                }
                CircularIndeterminateProgressBar(
                    isDisplayed = loading
                )
                DefaultSnackbar(
                    snackbarHostState = scaffoldState.snackbarHostState,
                    onDismiss = {
                        scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
                    },
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                )
            }
        }
    }
}