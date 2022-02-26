package com.example.mynoteapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.mynoteapp.Composables.MainScreen
import com.example.mynoteapp.Composables.SetUpNavigation
import com.example.mynoteapp.VIewModel.NoteVIewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val noteViewModel:NoteVIewModel by viewModels()
    private lateinit var navHostController: NavHostController
    @ExperimentalMaterialApi
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            navHostController= rememberNavController()
            SetUpNavigation(navHostController,noteViewModel)
        }
    }
}

