package com.kc.mvvmfoodrecipeapp.presentation.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.TextFieldDefaults.textFieldColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.kc.mvvmfoodrecipeapp.presentation.ui.recipe_list.RecipeListViewModel

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun HeaderScreen(viewModel: RecipeListViewModel?) {

    val query = viewModel?.query?.value
    val softKeyboardController = LocalSoftwareKeyboardController.current

    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        color = MaterialTheme.colors.onPrimary,
        elevation = 8.dp,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            if (query != null) {
                TextField(
                    value = query,
                    onValueChange = { newValue ->
                        viewModel.query.value = newValue
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
                            contentDescription = "image")
                    },
                    keyboardActions = KeyboardActions(onSearch = {
                        viewModel.newSearch(query)
                        softKeyboardController?.hide()
                    }),

                    textStyle = TextStyle(color = MaterialTheme.colors.onSurface),
                    colors = textFieldColors(backgroundColor = MaterialTheme.colors.surface)
                )
            }
                }
        }
    }
