package com.example.mynoteapp.Composables

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*


import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mynoteapp.Data.*
import com.example.mynoteapp.R
import com.example.mynoteapp.VIewModel.NoteVIewModel
import com.example.mynoteapp.ui.theme.NotDataColor
import com.example.mynoteapp.ui.theme.TopAppBarColor
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun MainScreen(noteViewModel: NoteVIewModel, NavigateToItemScene: (Int) -> Unit, action: Action) {

    var priority by remember{ mutableStateOf(Priority.NONE)}
    val allData by noteViewModel.allData.collectAsState()

val searchedData by noteViewModel.searchData.collectAsState()

    var searchText:String by remember {
        mutableStateOf("")
    }
    LaunchedEffect(key1 = searchText ){
        if(searchText.isNotEmpty()){
            noteViewModel.getSearchData(searchText)
        }
    }

    val actionPassed by remember{ mutableStateOf(action)}
    LaunchedEffect(key1 = true){
        noteViewModel.getAllData()
    }

    val scaffoldState = rememberScaffoldState()
    val corutineScope = rememberCoroutineScope()
    LaunchedEffect(key1 = actionPassed ){
        noteViewModel.handleAction(action )
        showSnackbar(scaffoldState,"$actionPassed",corutineScope,action) {
            noteViewModel.handleAction(
                it
            )
        }

    }
    val allDataSortedByHighPrioirty by noteViewModel.allDataOrederedByHighPrioirty.collectAsState()

    val allDataSortedByMediumPrioirty by noteViewModel.allDataOrederedByMediumPrioirty.collectAsState()

    val allDataSortedByLowPrioirty by noteViewModel.allDataOrederedByLowPrioirty.collectAsState()

    LaunchedEffect(key1 =priority ){
        when(priority){
            Priority.NONE->{noteViewModel.getAllData()}
            Priority.LOW->{ noteViewModel.getDataByPrioirtyLow()}
            Priority.MEDIUM->{ noteViewModel.getDataByPrioirtyMedium()}
            Priority.HIGH->{ noteViewModel.getDataByPrioirtyHigh()}
        }
    }
    Scaffold(scaffoldState = scaffoldState,topBar = { CreateTopBar(searchText,{searchText=it},{priority=it},noteViewModel) },modifier=Modifier.fillMaxSize(),floatingActionButton = { CreateFlotActionButton(NavigateToItemScene) }) {
        if(searchText.isEmpty())
        when(priority){
            Priority.NONE->{ ShowList(allData,NavigateToItemScene,{action,item->noteViewModel.updateFileds(item);noteViewModel.handleAction(action)})}
            Priority.LOW->{ ShowList(allDataSortedByLowPrioirty,NavigateToItemScene){action,item->noteViewModel.updateFileds(item);noteViewModel.handleAction(action)}}
            Priority.MEDIUM->{ ShowList(allDataSortedByMediumPrioirty,NavigateToItemScene){action,item->noteViewModel.updateFileds(item);noteViewModel.handleAction(action)}}
            Priority.HIGH->{ ShowList(allDataSortedByHighPrioirty,NavigateToItemScene){action,item->noteViewModel.updateFileds(item);noteViewModel.handleAction(action)}}
        }
        else ShowList(searchedData,NavigateToItemScene,{action,item->noteViewModel.updateFileds(item);noteViewModel.handleAction(action)})

   

}
}

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun ShowList(allData: Request, NavigateToItemScene: (Int) -> Unit,onmSwipeToDelete:(Action,Item)->Unit) {
when(allData){
    is Request.Success->{if(allData.data.isEmpty()) ShowEmptyScreen() else ContentScreen(allData,NavigateToItemScene,onSwipeToDelete = onmSwipeToDelete)}
    is Request.Loading->ShowLoadProgress()
    is Request.Error -> showToast(allData.errorMsg, LocalContext.current)
}
}

@Composable
fun ShowLoadProgress() {
    Log.i("cao","Show Loading ")
  //  CircularProgressIndicator(color = Color.Red,modifier = Modifier.size(20.dp))
}



@Composable
fun ShowEmptyScreen() {
  Column(modifier=Modifier.fillMaxSize()) {
      Box(
          Modifier
              .weight(1f)
              .fillMaxWidth()
              .padding(10.dp),contentAlignment = Alignment.Center){
          Icon(painter = painterResource(id = R.drawable.sad),"",tint=MaterialTheme.colors.NotDataColor,modifier = Modifier.fillMaxWidth(0.5f))

      }
      Box(modifier= Modifier
          .weight(4f)
          .fillMaxWidth(),contentAlignment = Alignment.TopCenter){
          Text("No Data",color = MaterialTheme.colors.NotDataColor,fontSize = 20.sp)
      }
      
  }
}

@Composable
fun CreateFlotActionButton(NavigateToItemScene:(Int)->Unit) {
    FloatingActionButton(onClick = { NavigateToItemScene(-1) },backgroundColor = MaterialTheme.colors.TopAppBarColor) {
        Icon(painterResource(id = R.drawable.add),"",tint=MaterialTheme.colors.onPrimary)
    }
}



@Composable
fun RedBackground(rotate:Float){
    Card(modifier= Modifier
        .fillMaxSize()
        ,shape = RoundedCornerShape(10.dp),backgroundColor = Color.Red){
        Box(modifier= Modifier
            .fillMaxSize()
            .padding(10.dp),contentAlignment = Alignment.CenterEnd){
            Icon(painterResource(id = R.drawable.trash),"",tint = Color.White,modifier=Modifier.rotate((rotate)))
        }

    }
}

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun   ContentScreen(allData: Request.Success, NavigateToItemScene: (Int) -> Unit,onSwipeToDelete:(Action,Item)->Unit) {

    var showDialog by remember{ mutableStateOf(false)}
    var passwordToCheck by remember {
        mutableStateOf("")
    }
    var id by remember {
        mutableStateOf(-1)
    }
    if(showDialog ) CreateLoginDialog(NavigateToItemScene,id,passwordToCheck,showDialog) { showDialog = it }
    LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp)
        ) {
            items((allData).data, key = { it.id }) { itemList ->
                val dismissState = rememberDismissState()
                val rotate by animateFloatAsState(targetValue = if (dismissState.targetValue == DismissValue.Default) 0f else -45f)
                val isDissmissed= dismissState.isDismissed(DismissDirection.EndToStart)
                if(isDissmissed){
                    val scope= rememberCoroutineScope()
                    scope.launch {
                        //jer animacije traju 300ms moramo da sacekamo pre nego sto izbrisemo da vidimo animaciju
                        delay(300)
                        onSwipeToDelete(Action.DELETE,itemList)
                    }

                }
                var itemAppeard by remember{ mutableStateOf(false)}
                LaunchedEffect(key1 = true){
                    itemAppeard=true
                }
                //animated visisibility je za animaciju itema kad ase stvaraju zato ide launcheffect za true
              AnimatedVisibility(visible =itemAppeard && !isDissmissed,exit = shrinkVertically(animationSpec = tween(300)) ,enter = expandVertically(animationSpec = tween(durationMillis = 300)) ) {
                  SwipeToDismiss(
                      state = dismissState,
                      background = { RedBackground(rotate = rotate) },
                      directions = setOf(DismissDirection.EndToStart),
                      dismissThresholds = { FractionalThreshold(0.2f) }) {


                      Element(
                          itemList.title,
                          itemList.description,
                          itemList.lockState,
                          itemList.priority,
                          itemList.id,
                          itemList.date!!
                      ) {
                          if (itemList.lockState == LockState.UNLOCKED) NavigateToItemScene(
                              itemList.id
                          )
                          else {
                              showDialog = true
                              passwordToCheck = itemList.locked!!.password
                              id = itemList.id

                          }
                      }

                  }
              }
                Spacer(modifier = Modifier.height(10.dp))
            }
        }

}

