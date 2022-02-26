package com.example.mynoteapp.Composables

import androidx.compose.foundation.Canvas

import androidx.compose.foundation.layout.*

import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

import com.example.mynoteapp.Data.Priority





@Composable
fun ShowSort(ChangePrioirty:(Priority)->Unit,sortMenu: Boolean, ChangeSortMenu:(Boolean)->Unit, showFillSize: Boolean) {
    DropdownMenu(expanded = sortMenu, onDismissRequest = {ChangeSortMenu(false) },modifier = if(showFillSize) Modifier.fillMaxWidth(0.8f) else Modifier.width(IntrinsicSize.Max)) {
        DropdownMenuItem(onClick = {ChangeSortMenu(false);ChangePrioirty(Priority.HIGH);}) {
            CreateSortItem(Color.Red,"High",showFillSize)
        }
        DropdownMenuItem(onClick = {ChangeSortMenu(false);ChangePrioirty(Priority.MEDIUM);}) {

                CreateSortItem(Color.Yellow,"Medium",showFillSize)

        }
        DropdownMenuItem(onClick = {ChangeSortMenu(false);ChangePrioirty(Priority.LOW);}) {
            CreateSortItem(Color.Green,"Low",showFillSize)
        }
        DropdownMenuItem(onClick = {ChangeSortMenu(false);ChangePrioirty(Priority.NONE);}) {
            CreateSortItem(Color.Gray,"None",showFillSize)
        }

    }
}

@Composable
fun CreateSortItem(color:Color,text:String,showFillSize:Boolean){
    Row(modifier = Modifier.wrapContentWidth(),horizontalArrangement = Arrangement.SpaceEvenly){
        Canvas(modifier =if(!showFillSize) Modifier
            .weight(1f)
            .align(Alignment.CenterVertically) else Modifier
            .weight(1f)
            .fillMaxWidth()
            .align(Alignment.CenterVertically)){
drawCircle(color,20f)
        }
        Spacer(modifier = Modifier.weight(1f))
        Text(text,modifier = Modifier.weight(5f),maxLines = 1,softWrap = false)
    }
}

@Composable
fun ShowMore(openMenu:Boolean,changeOpenMenu:(Boolean)->Unit,showDialogDelete:(Boolean)->Unit){
    DropdownMenu(expanded = openMenu, onDismissRequest = {showDialogDelete(false);changeOpenMenu(false) }) {
        DropdownMenuItem(onClick = {showDialogDelete(true);changeOpenMenu(false);}) {
            Text("Delete All")
        }

    }
}