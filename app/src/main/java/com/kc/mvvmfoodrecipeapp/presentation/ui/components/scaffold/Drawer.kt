package com.kc.mvvmfoodrecipeapp.presentation.ui.components.scaffold

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun MyDrawer() {
    Column() {
        Text(text = "Item1")
        Text(text = "Item2")
        Text(text = "Item3")
        Text(text = "Item4")
        Text(text = "Item5")
    }
}