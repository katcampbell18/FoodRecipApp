package com.kc.mvvmfoodrecipeapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import com.kc.mvvmfoodrecipeapp.Navigation
import com.kc.mvvmfoodrecipeapp.presentation.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

//       custom theme for app
            AppTheme(darkTheme = isSystemInDarkTheme() ) {
                Navigation()
            }
        }
    }
}
