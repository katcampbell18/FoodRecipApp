package com.kc.mvvmfoodrecipeapp.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.kc.mvvmfoodrecipeapp.R
import com.kc.mvvmfoodrecipeapp.data.model.RecipeItem

@Composable
fun RecipeCard(
    recipe: RecipeItem,
    onClick: () -> Unit,
//    navController: NavController
){
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .padding(
                bottom = 6.dp,
                top = 6.dp
            )
            .fillMaxWidth()
            .clickable {
                onClick()
//                       navController.navigate(Screen.RecipeDetailScreen.route)
            },
        elevation = 8.dp,
    ) {
        Column{
            recipe.featuredImage?.let{url ->
                Image(
                    painter = rememberImagePainter(
                        data = recipe.featuredImage,
                    builder = {
                        placeholder(R.drawable.empty_plate)
                    }),
                    contentDescription = "",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(255.dp),
                    contentScale = ContentScale.Crop
                )
            }
            recipe.title?.let { title ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = 12.dp,
                            bottom = 12.dp,
                            start = 8.dp,
                            end = 8.dp
                        )
                ){
                    Text(
                        text = title,
                        modifier = Modifier
                            .fillMaxWidth(0.85f)
                            .wrapContentWidth(Alignment.Start),
                        style = MaterialTheme.typography.h5
                    )
                    Spacer(
                        modifier = Modifier
                            .padding(3.dp)
                    )
                    Text(
                        text = recipe.rating.toString(),
                        modifer = Modifier
                            .fillMaxWidth()
                            .wrapContentWidth(Alignment.End)
                            .align(Alignment.CenterVertically),
                        style = MaterialTheme.typography.h6
                    )
                }
            }
        }
    }
}