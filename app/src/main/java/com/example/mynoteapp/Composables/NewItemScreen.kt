package com.example.mynoteapp.Composables

import android.content.Context
import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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

@Composable
fun NewItemScreen(noteVIewModel: NoteVIewModel, NavigateToMainScene: () -> Unit, context: Context= LocalContext.current){
    val action by noteVIewModel.action.collectAsState()

    var id=-1
    var title  by remember{ mutableStateOf("") }
    var description by remember{ mutableStateOf("") }
    var priority by remember{ mutableStateOf(Priority.LOW) }
    var lockedState by remember{ mutableStateOf(LockState.UNLOCKED)}
var password:String by remember{ mutableStateOf("")}
var showDialog by remember{ mutableStateOf(false)}
    if(showDialog) CreatePasswordDialog ({ showDialog = it },password,{password=it;lockedState =
        if(password.trim().isEmpty()
    ) LockState.UNLOCKED else LockState.LOCKED
    })
   Log.i("cao",password)
    Scaffold(
        topBar = {
            CreateTopBarNewItem({ noteVIewModel.changeAction(it);  NavigateToMainScene() }, {

                if (noteVIewModel.validateFields(title, description)) {

                    noteVIewModel.updateFileds(
                        id,
                        title,
                        description,
                        priority,
                        Calendar.getInstance(),
                        lockState = lockedState,
                        locked = if(password.isNotEmpty()) Locked(password) else null
                    )
                    noteVIewModel.changeAction(Action.ADD)
                    NavigateToMainScene()
                } else
                    showToast(
                        "Field Empty",
                        context
                    )
            }, lockedState, {showDialog=it})
        },
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
    ) {
        Column(modifier= Modifier
            .padding(10.dp)
            .fillMaxHeight(),verticalArrangement = Arrangement.SpaceAround,horizontalAlignment = Alignment.CenterHorizontally
        ) {

            TextField(value = title, onValueChange ={title=it},modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color.Gray, shape = MaterialTheme.shapes.medium),colors = TextFieldDefaults.textFieldColors(textColor = MaterialTheme.colors.TextColor,errorIndicatorColor = Color.Transparent,unfocusedIndicatorColor = Color.Transparent,focusedIndicatorColor = Color.Transparent,disabledIndicatorColor = Color.Transparent,backgroundColor = Color.Transparent),label = { Text("Title") } )
            PriorityList(priority) { priority = it }
            TextField(value = description, onValueChange ={description=it},modifier = Modifier

                .fillMaxWidth()
                .fillMaxHeight(0.7f)
                .border(1.dp, Color.Gray, shape = MaterialTheme.shapes.medium),colors = TextFieldDefaults.textFieldColors(textColor = MaterialTheme.colors.TextColor,errorIndicatorColor = Color.Transparent,unfocusedIndicatorColor = Color.Transparent,focusedIndicatorColor = Color.Transparent,disabledIndicatorColor = Color.Transparent,backgroundColor = Color.Transparent),label = { Text("Description") } )


        }
    }

}



