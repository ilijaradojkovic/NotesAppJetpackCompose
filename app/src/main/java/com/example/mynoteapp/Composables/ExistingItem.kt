package com.example.mynoteapp.Composables

import android.content.Context
import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.mynoteapp.Data.Action
import com.example.mynoteapp.Data.LockState
import com.example.mynoteapp.Data.Locked
import com.example.mynoteapp.Data.Priority
import com.example.mynoteapp.VIewModel.NoteVIewModel
import com.example.mynoteapp.ui.theme.TextColor
import java.util.*


@ExperimentalAnimationApi
@Composable
fun ExistingItemScreen(
    noteVIewModel: NoteVIewModel,
    NavigateToMainScene: () -> Unit,
    id: Int,
    context: Context= LocalContext.current


){
    val action by noteVIewModel.action.collectAsState()

    val spesificItem by noteVIewModel.spesificData.collectAsState()
    LaunchedEffect(key1 = id ){
       noteVIewModel.getSpecificNote(id)
    }
    //ovde imam spesific Data

    var title  by remember{ mutableStateOf(noteVIewModel.title)}
    var description by remember{ mutableStateOf(noteVIewModel.description)}
    var priority by remember{ mutableStateOf(noteVIewModel.priority)}
    var lockState by remember{ mutableStateOf(noteVIewModel.lockState)}
    var locked by remember{ mutableStateOf<Locked?>(noteVIewModel.locked)}
    var password:String by remember{ mutableStateOf(noteVIewModel.password)}
    var id=-1;
    var showDialogLock by remember{ mutableStateOf(false)}
    var showDialogDelete by remember{ mutableStateOf(false)}

    if(showDialogLock) CreatePasswordDialog ({ showDialogLock = it },password,{password=it;lockState =
        if(password.trim().isEmpty()
        ) LockState.UNLOCKED else LockState.LOCKED
    })
    if(showDialogDelete) CreateDialogAlert(
        title = "Delete $title",
        message = "Are you sure?",
        onDeleteClicked ={noteVIewModel.deleteData(spesificItem);noteVIewModel.changeAction(Action.DELETE);  NavigateToMainScene()} ,
        closeDialog = {showDialogDelete=it},
        action = Action.DELETE
    )
    LaunchedEffect(key1 = spesificItem ){
        if(spesificItem!=null) {
            id=spesificItem.id
            title = spesificItem.title
            description = spesificItem.description
            priority = spesificItem.priority
            lockState = spesificItem.lockState
            locked = spesificItem.locked
            password = spesificItem.locked?.password ?: ""
        }
    }
    Scaffold(topBar = {CreateTopBarExistingItem( {noteVIewModel.changeAction(it);  NavigateToMainScene()},{

        if(noteVIewModel.validateFields(title,description)){
            noteVIewModel.updateFileds(id,title,description,priority, Calendar.getInstance(),lockState = lockState,if(password.isNotEmpty()) Locked(password) else null)

           noteVIewModel.changeAction(Action.UPDATE)
            NavigateToMainScene()
        }else
            showToast("Field Empty",
                context)} ,lockState,{showDialogLock=it},{showDialogDelete=it})},modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(),) {
        Column(modifier= Modifier
            .padding(10.dp)
            .fillMaxHeight(),verticalArrangement = Arrangement.SpaceAround,horizontalAlignment = Alignment.CenterHorizontally
        ) {

            TextField(value = title, onValueChange ={title=it},modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color.Gray, shape = MaterialTheme.shapes.medium),colors = TextFieldDefaults.textFieldColors(textColor = MaterialTheme.colors.TextColor,errorIndicatorColor = Color.Transparent,unfocusedIndicatorColor = Color.Transparent,focusedIndicatorColor = Color.Transparent,disabledIndicatorColor = Color.Transparent,backgroundColor = Color.Transparent),label = {Text("Title")} )
            PriorityList(priority) { priority = it }
            TextField(value = description, onValueChange ={description=it},modifier = Modifier

                .fillMaxWidth()
                .fillMaxHeight(0.7f)
                .border(1.dp, Color.Gray, shape = MaterialTheme.shapes.medium),colors = TextFieldDefaults.textFieldColors(textColor = MaterialTheme.colors.TextColor,errorIndicatorColor = Color.Transparent,unfocusedIndicatorColor = Color.Transparent,focusedIndicatorColor = Color.Transparent,disabledIndicatorColor = Color.Transparent,backgroundColor = Color.Transparent),label = {Text("Description")} )


        }
    }

}

@Composable
fun PriorityList(priority:Priority,changePriority:(Priority)->Unit) {
    var expanded by remember{ mutableStateOf(false)}
    val angle by animateFloatAsState(if(expanded)180f else 0f)

    Row(modifier = Modifier
        .fillMaxWidth()
        .background(Color.White)
        .height(60.dp)
        .clickable { expanded = true }
        .border(1.dp, Color.Gray, shape = MaterialTheme.shapes.small),verticalAlignment = Alignment.CenterVertically,horizontalArrangement = Arrangement.Center){

        Canvas(modifier = Modifier
            .size(16.dp)
            .weight(1f)){
            drawCircle(priority.color)
        }

        Text(priority.name,modifier = Modifier.weight(1f))
        Box(modifier = Modifier.weight(4f),contentAlignment = Alignment.CenterEnd){
            IconButton(onClick = {expanded=true},modifier = Modifier.rotate(angle)) {
                Icon(Icons.Filled.ArrowDropDown,"ArrowPriority")
            }
        }
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded=false },modifier = Modifier.fillMaxWidth()) {
            DropdownMenuItem(onClick = {expanded=false;changePriority(Priority.LOW)}) {
                PriorityItem(priority = (Priority.LOW))
            }
            DropdownMenuItem(onClick = {expanded=false;changePriority(Priority.MEDIUM)}) {
                PriorityItem(priority = (Priority.MEDIUM))
            }
            DropdownMenuItem(onClick = {expanded=false;changePriority(Priority.HIGH)}) {
                PriorityItem(priority = (Priority.HIGH))
            }
        }



    }
}
@Composable
fun PriorityItem(priority: Priority){
    Row(verticalAlignment = Alignment.CenterVertically){
        Canvas(modifier = Modifier.size(16.dp)){
            drawCircle(color= priority.color)
        }
        Text(modifier=Modifier.padding(start=8.dp), text=priority.name,color= MaterialTheme.colors.onSurface)
    }
}
