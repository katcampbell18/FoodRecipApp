package com.kc.mvvmfoodrecipeapp.presentation.ui.components.scaffold

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@Composable
fun MyBottomBar(
//    TODO: add navigation
//    navController: NavController
){
    BottomNavigation(
        elevation = 12.dp
    ){
        BottomNavigationItem(
            icon = { Icon(imageVector = Icons.Default.Home,
                contentDescription = "can't display icon")
            } ,
            selected = false,
            onClick ={
//                     navController.navigate(Screen.RecipeListScreen.route)
            },
        )
        BottomNavigationItem(
            icon = { Icon(imageVector = Icons.Default.Search,
                contentDescription = "search icon")
            } ,
            selected = true,
            onClick ={},
        )
        BottomNavigationItem(
            icon = { Icon(imageVector = Icons.Default.AccountBox,
                contentDescription = "account icon")
            } ,
            selected = false,
            onClick ={},
        )
    }
}