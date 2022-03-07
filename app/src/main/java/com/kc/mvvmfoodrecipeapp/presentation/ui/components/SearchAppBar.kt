package com.kc.mvvmfoodrecipeapp.presentation.ui

import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.TextFieldDefaults.textFieldColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.kc.mvvmfoodrecipeapp.presentation.ui.components.FoodCategoryChip
import com.kc.mvvmfoodrecipeapp.presentation.ui.recipe_list.FoodCategory
import com.kc.mvvmfoodrecipeapp.presentation.ui.recipe_list.RecipeListViewModel
import com.kc.mvvmfoodrecipeapp.presentation.ui.recipe_list.getAllFoodCategories
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchAppBar(
    query: String,
    onQueryChanged: (String) -> Unit,
    onExecuteSearch: () -> Unit,
    categoryScrollPosition: Int,
    selectedCategory: FoodCategory?,
    onSelectedCategoryChanged: (String) -> Unit,
    onChangeCategoryScrollPosition: (Int) -> Unit,
    onToggleTheme: () -> Unit,
    ) {

    val softKeyboardController = LocalSoftwareKeyboardController.current

    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        color = MaterialTheme.colors.surface,
        elevation = 8.dp,
    ) {
        Column() {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                if (query != null) {
                    TextField(
                        value = query,
                        onValueChange = {
                            onQueryChanged(it)  //it = query
                        },
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .padding(8.dp),
                        label = {
                            Text(
                                text = "Search"
                            )
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Search
                        ),
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Filled.Search,
                                contentDescription = "image"
                            )
                        },
                        keyboardActions = KeyboardActions(onSearch = {
                            onExecuteSearch()
                            softKeyboardController?.hide()
                        }),

                        textStyle = MaterialTheme.typography.button,
                        colors = textFieldColors(backgroundColor = MaterialTheme.colors.surface)
                    )
                }
                ConstraintLayout(
                    modifier = Modifier.align(Alignment.CenterVertically)
                ){
                    val menu = createRef()
                    IconButton(onClick = onToggleTheme,
                        modifier = Modifier
                            .constrainAs(menu) {
                                end.linkTo(parent.end)
                                top.linkTo(parent.top)
                                bottom.linkTo(parent.bottom)
                            }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.MoreVert,
                            contentDescription = "theme button"
                            )
                    }
                }
            }

            val scrollState = rememberScrollState()
            val coroutineScope = rememberCoroutineScope()
            Row(
                modifier = Modifier
                    .horizontalScroll(scrollState)
                    .fillMaxWidth()
                    .padding(start = 8.dp, bottom = 8.dp)
            ) {
                coroutineScope.launch {
                        scrollState.scrollTo(categoryScrollPosition)
                }
                for (category in getAllFoodCategories()) {
                    FoodCategoryChip(
                        category = category.value,
                        isSelected = selectedCategory == category,
                        onSelectedCategoryChanged = {
                                onSelectedCategoryChanged(it)  //it = category.value
                                onChangeCategoryScrollPosition(scrollState.value)
                                                    },
                        onExecuteSearch = {
                                onExecuteSearch()
                                }
                            )
                        }
                }
            }
        }
    }

