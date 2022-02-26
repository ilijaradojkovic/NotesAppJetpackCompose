package com.example.mynoteapp.Composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.mynoteapp.Data.Action
import com.example.mynoteapp.R
import com.example.mynoteapp.ui.theme.TopAppBarColor


@Composable
fun CreatePasswordDialog(ChangeShowDialog:(Boolean)->Unit,password:String,ChangePassword:(String)->Unit){
    var text by remember{ mutableStateOf(password)}
    var isPassVisible by remember{ mutableStateOf(false)}
    val transform = if(isPassVisible) VisualTransformation.None else PasswordVisualTransformation()

    Dialog(onDismissRequest = {  ChangeShowDialog(false)}) {
Surface(shape = RoundedCornerShape(10.dp),modifier = Modifier
    .fillMaxWidth()
    .fillMaxHeight(0.6f)
    .padding(10.dp)) {
    Column(modifier= Modifier
        .fillMaxWidth()
        .fillMaxHeight()
        .background(color = MaterialTheme.colors.TopAppBarColor)
       ,verticalArrangement = Arrangement.SpaceEvenly,horizontalAlignment = Alignment.CenterHorizontally) {
        Text(modifier = Modifier.weight(2f),text = "Lock Note",fontSize = 20.sp,fontWeight = FontWeight.Bold,color = Color.White)
        TextField(trailingIcon = { IconButton(onClick = {isPassVisible=!isPassVisible}) {
            if(isPassVisible)  Icon(painter = painterResource(id = R.drawable.eye),"")

            else Icon(painter = painterResource(id = R.drawable.eye_closed),"")
            
        }},value = text, onValueChange ={text=it},visualTransformation = transform,modifier=Modifier.weight(1f),colors = TextFieldDefaults.textFieldColors(textColor = Color.DarkGray,backgroundColor = Color.White,disabledIndicatorColor = Color.Transparent,focusedIndicatorColor = Color.Transparent,unfocusedIndicatorColor = Color.Transparent,errorIndicatorColor = Color.Transparent) )
        Box(modifier = Modifier.weight(1f),contentAlignment = Alignment.CenterEnd){
            Text("*Enter password to lock your note",color = Color.White,fontWeight = FontWeight.Bold)
        }
        Row(modifier= Modifier
            .weight(2f)
            .padding(8.dp),verticalAlignment = Alignment.Bottom,horizontalArrangement = Arrangement.SpaceBetween) {
            Button(onClick = { ChangeShowDialog(false) },modifier = Modifier.weight(1f),colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)) {
                Text("Cancle",color = Color.DarkGray)
            }
            if(password.isNotEmpty())
                Button(onClick = {ChangePassword("");ChangeShowDialog(false); },modifier = Modifier.weight(1f),colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)) {
                    Text("Unlock",color = Color.DarkGray)
                }

            else Spacer(Modifier.weight(1f))
            Button(onClick = {ChangePassword(text); ChangeShowDialog(false); },modifier = Modifier.weight(1f),colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)) {
                Text("Lock",color = Color.DarkGray)
            }

        }
    }

}
}



}


@Composable
fun CreateLoginDialog(NavigateToTaskScreen:(Int)->Unit,id:Int,passwordToCheck:String,showDialog: Boolean, ChangeShowDialog: (Boolean) -> Unit) {
    var text by remember{ mutableStateOf("")}
    var isPassVisible by remember{ mutableStateOf(showDialog)}
    val context= LocalContext.current
    val transform = if(isPassVisible) VisualTransformation.None else PasswordVisualTransformation()
    Dialog(onDismissRequest = { ChangeShowDialog(false) }) {
        Surface(
            shape = RoundedCornerShape(10.dp), modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.6f)
                .padding(10.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(color = MaterialTheme.colors.TopAppBarColor),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier.weight(2f),
                    text = "Note Locked",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                TextField(
                    trailingIcon = {
                        IconButton(onClick = { isPassVisible = !isPassVisible }) {
                            if (isPassVisible) Icon(
                                painter = painterResource(id = R.drawable.eye),
                                ""
                            )
                            else Icon(painter = painterResource(id = R.drawable.eye_closed), "")

                        }
                    },
                    value = text,
                    onValueChange = { text = it },
                    visualTransformation = transform,
                    modifier = Modifier.weight(1f),
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = Color.DarkGray,
                        backgroundColor = Color.White,
                        disabledIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        errorIndicatorColor = Color.Transparent
                    )
                )
                Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.CenterEnd) {
                    Text(
                        "*Enter password to unlock note",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
                Row(
                    modifier = Modifier
                        .weight(2f)
                        .padding(8.dp),
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = { ChangeShowDialog(false)},
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)
                    ) {
                        Text("Cancle", color = Color.DarkGray)
                    }

                    Spacer(modifier = Modifier.weight(1f))
                    Button(
                        onClick = {
                                  if(passwordToCheck == text){
                                      ChangeShowDialog(false)
                                      NavigateToTaskScreen(id)
                                  }else{
                                      showToast( "Password Incorect",context)
                                  }
                        },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)
                    ) {
                        Text("Unlock", color = Color.DarkGray)
                    }

                }
            }
        }
    }
}



@Composable
fun CreateDialogAlert(title:String,message:String,onDeleteClicked:(Action)->Unit,closeDialog:(Boolean)->Unit,action: Action){
    AlertDialog(modifier = Modifier.padding(10.dp),onDismissRequest = {closeDialog(false)},buttons = {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),verticalAlignment = Alignment.CenterVertically,horizontalArrangement = Arrangement.SpaceEvenly){
            Button(onClick = { closeDialog(false) },colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)) {

                Text("Cancle")
            }
            Button(onClick = { onDeleteClicked(action) },colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)) {
                Text("Delete")
            }
        }

    },title = {Text(title,modifier = Modifier.fillMaxWidth(),textAlign = TextAlign.Center,color = Color.White,fontWeight = FontWeight.Bold,fontSize = 20.sp)},
        backgroundColor = MaterialTheme.colors.TopAppBarColor,
        shape = RoundedCornerShape(10.dp),
        text = {Text(message,modifier = Modifier.fillMaxWidth(),textAlign = TextAlign.Center,color = Color.White,fontSize = 13.sp)})
}