package com.kc.mvvmfoodrecipeapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.kc.mvvmfoodrecipeapp.Navigation
import com.kc.mvvmfoodrecipeapp.ui.theme.MVVMFoodRecipeAppTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MVVMFoodRecipeAppTheme() {
                Navigation()
            }
        }
    }
}
