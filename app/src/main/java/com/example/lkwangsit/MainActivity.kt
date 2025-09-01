package com.example.lkwangsit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.lkwangsit.navigation.AppNavHost
import com.example.lkwangsit.theme.LKWangsitTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LKWangsitTheme {
                Surface(modifier = Modifier) {
                    AppNavHost()
                }
            }
        }
    }
}