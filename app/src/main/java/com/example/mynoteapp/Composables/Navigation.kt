package com.example.mynoteapp.Composables

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.*
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import com.example.mynoteapp.Data.Constants
import com.example.mynoteapp.Data.toAction
import com.example.mynoteapp.VIewModel.NoteVIewModel

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun SetUpNavigation(navHostController: NavHostController,noteVIewModel: NoteVIewModel) {

    val screen=remember(navHostController){
        Screens(navHostController = navHostController)
    }
    NavHost(navController = navHostController, startDestination = Constants.MainScreenPath) {
        NavitageMainScreen(screen.navigateToItemScene,noteVIewModel)
        NavitageItem(screen,noteVIewModel)
    }
}

@ExperimentalAnimationApi
@ExperimentalMaterialApi
fun NavGraphBuilder.NavitageMainScreen(NavigateToItemScene: (Int) -> Unit, noteVIewModel: NoteVIewModel){
composable(Constants.MainScreenPath){

    val action by  noteVIewModel.action.collectAsState()



        MainScreen(noteVIewModel,NavigateToItemScene,action.toString().toAction())
}
}
@ExperimentalAnimationApi
fun NavGraphBuilder.NavitageItem(screens: Screens, noteVIewModel: NoteVIewModel){
    composable(Constants.ItemScenePath,listOf(navArgument(Constants.idItem) {
        nullable=false
        type= NavType.IntType
        defaultValue=-1
    })){
        val id=it.arguments?.getInt(Constants.idItem)

       if(id!=-1 && id!=null)
            ExistingItemScreen(noteVIewModel,screens.navigateToMainScene,id)
        else
            NewItemScreen(noteVIewModel,screens.navigateToMainScene)
    }

}

class Screens(navHostController: NavHostController){
    val navigateToMainScene={
        navHostController.navigate(Constants.MainScreen){
            popUpTo(Constants.MainScreenPath){inclusive=true}
        }
    }
    val navigateToItemScene:(Int)->Unit={
        navHostController.navigate("${Constants.ItemScene}/${it}")
    }
}

