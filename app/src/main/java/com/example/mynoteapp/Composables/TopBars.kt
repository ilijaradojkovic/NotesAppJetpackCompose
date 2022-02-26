package com.example.mynoteapp.Composables

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.mynoteapp.Data.Action
import com.example.mynoteapp.Data.LockState
import com.example.mynoteapp.Data.Priority
import com.example.mynoteapp.R
import com.example.mynoteapp.VIewModel.NoteVIewModel
import com.example.mynoteapp.ui.theme.TopAppBarColor
import kotlinx.coroutines.delay


@ExperimentalAnimationApi
@Composable
fun CreateTopBar(searchText:String,ChangeText:(String)->Unit,ChangePrioirty:(Priority)->Unit,noteVIewModel: NoteVIewModel){
    var searchOpened by remember{ mutableStateOf(false) }

    var animateIconsDefaultTopBar by remember{ mutableStateOf(false)}
    var animateIconsSearchTopBar by remember{ mutableStateOf(false)}

    LaunchedEffect(key1 = searchOpened ) {

        if (searchOpened) {
            animateIconsDefaultTopBar = false
            delay(300)
            animateIconsSearchTopBar = true
        } else {

            animateIconsDefaultTopBar = true
            delay(300)
            animateIconsSearchTopBar = false
        }
    }

        TopAppBar(
            modifier = Modifier.fillMaxWidth(),
            backgroundColor = MaterialTheme.colors.TopAppBarColor
        ) {
            if (!searchOpened)
                MenuTopBar(animateIconsDefaultTopBar ,ChangePrioirty, noteVIewModel) { searchOpened = it }
            else
                SearchTopBar(animateIconsSearchTopBar,searchText, ChangeText) { searchOpened = it;ChangeText("")}
        }
    }



@ExperimentalAnimationApi
@Composable
fun SearchTopBar(animateIconsSearchTopBar:Boolean,searchText:String,CahngeSearchText:(String)->Unit,ChangeToMenuTopBar:(Boolean)->Unit) {
    AnimatedVisibility(visible =animateIconsSearchTopBar ,enter = expandHorizontally(animationSpec = tween(300)),exit = shrinkHorizontally(animationSpec = tween(300))) {

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Icon(
                painterResource(id = R.drawable.magnifier), "",
                tint = Color.White, modifier = Modifier.weight(1f)
            )
            Box(
                Modifier
                    .weight(2f)
                    .border(1.dp, Color.White, shape = RoundedCornerShape(10.dp))
            ) {
                BasicTextField(
                    maxLines = 1,
                    singleLine = true,
                    value = searchText,
                    onValueChange = { CahngeSearchText(it) },
                    decorationBox = { inner ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp)
                        ) {
                            inner()
                        }
                    },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(onSearch = {}),
                    textStyle = TextStyle(color = Color.White)
                )


            }


            IconButton(onClick = { ChangeToMenuTopBar(false) }, modifier = Modifier.weight(1f)) {
                Icon(painterResource(id = R.drawable.cancel), "", tint = Color.White)
            }

        }
    }
}

@ExperimentalAnimationApi
@Composable
fun MenuTopBar(animateIconsDefaultTopBar:Boolean,ChangePrioirty:(Priority)->Unit,noteVIewModel: NoteVIewModel, ChangeToSearchBar:(Boolean)->Unit){
    var opetMenu by remember{ mutableStateOf(false)}
    var sortMenu by remember{ mutableStateOf(false)}
    var deleteAllMenu by remember{ mutableStateOf(false)}

    if(deleteAllMenu) CreateDialogAlert(
        title = "Delete all notes",
        message = "Are you sure?",
        onDeleteClicked = {noteVIewModel.deleteAll();deleteAllMenu=false},
        closeDialog ={deleteAllMenu=it} ,
        action = Action.DELETE_ALL
    )

    AnimatedVisibility(visible =animateIconsDefaultTopBar ,enter = expandHorizontally(animationSpec = tween(300)),exit = shrinkHorizontally(animationSpec = tween(300))) {

        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { ChangeToSearchBar(true) }, modifier = Modifier.size(26.dp)) {
                Icon(painterResource(id = R.drawable.magnifier), "", tint = Color.White)

            }
            Spacer(modifier = Modifier.size(26.dp))
            IconButton(onClick = { sortMenu = !sortMenu }, modifier = Modifier.size(26.dp)) {
                Icon(painterResource(id = R.drawable.sort), "", tint = Color.White)
                ShowSort(ChangePrioirty, sortMenu, { it -> sortMenu = it }, false)

            }
            Spacer(modifier = Modifier.size(26.dp))
            IconButton(onClick = { opetMenu = !opetMenu }, modifier = Modifier.size(26.dp)) {
                Icon(painterResource(id = R.drawable.more), "", tint = Color.White)
                ShowMore(opetMenu, { it -> opetMenu = it }, { deleteAllMenu = it })
            }

            Spacer(modifier = Modifier.size(16.dp))

        }
    }

    }


@ExperimentalAnimationApi
@Composable
fun CreateTopBarExistingItem( onBackPressed:(Action)->Unit, onSaveClicked:()->Unit, locked: LockState, showDialogLock:(Boolean)->Unit,showDialogDelete:(Boolean)->Unit
                            ){


    TopAppBar(title = { Text("Title", color= Color.White,fontWeight = FontWeight.Bold) }
        , modifier = Modifier.fillMaxWidth(),
        navigationIcon ={ IconButton( onClick = { onBackPressed(Action.NONE)}){ Icon(
            painterResource(id = R.drawable.arrow_back),"",
            tint= Color.White)
        } },
        backgroundColor = MaterialTheme.colors.TopAppBarColor,
        actions = {

                if(locked== LockState.UNLOCKED)
                    IconButton(onClick = {showDialogLock(true)}){ Icon(painter = painterResource(id = R.drawable.unlocked),"",tint= Color.White) }
                else   IconButton(onClick = {showDialogLock(true)}){ Icon(painter = painterResource(id = R.drawable.lock),"",tint= Color.White) }

                IconButton(onClick = {   showDialogDelete(true)}){ Icon(painter = painterResource(id = R.drawable.trash),"",tint= Color.White) }
                IconButton(onClick = {onSaveClicked()}){ Icon(painter = painterResource(id = R.drawable.save),"",tint= Color.White) }




        } )


}
@Composable
fun CreateTopBarNewItem(
    onBackPressed: (Action) -> Unit,
    saveData: () -> Unit,
    locked: LockState,
    showDialog:(Boolean)->Unit
){
    TopAppBar(title = { Text("New Item", color= Color.White,fontWeight = FontWeight.Bold) }
        , modifier = Modifier.fillMaxWidth(),
        navigationIcon ={ IconButton( onClick = {onBackPressed(Action.NONE) }){ Icon(
            painterResource(id = R.drawable.cancel),"",
            tint= Color.White)
        } },
        backgroundColor = MaterialTheme.colors.TopAppBarColor,
        actions = {
            if(locked== LockState.UNLOCKED)
            IconButton(onClick = {showDialog(true)}){ Icon(painter = painterResource(id = R.drawable.unlocked),"",tint= Color.White) }
            else   IconButton(onClick = {showDialog(true)}){ Icon(painter = painterResource(id = R.drawable.lock),"",tint= Color.White) }

            IconButton(onClick = {saveData()}){ Icon(painter = painterResource(id = R.drawable.save),"",tint= Color.White) }


        } )


}