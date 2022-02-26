package com.example.mynoteapp.Composables

import android.content.Context
import android.widget.Toast
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarResult
import com.example.mynoteapp.Data.Action
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


fun showToast(message:String,context: Context){
    Toast.makeText(context,message,Toast.LENGTH_SHORT).show()
}

fun showSnackbar(scaffoldState: ScaffoldState,
                 message: String,
                 corutineScope: CoroutineScope,
                 action:Action,
onUndoClicked:(Action)->Unit){
    if(action!=Action.NONE){
    corutineScope.launch {
        val snack = scaffoldState.snackbarHostState.showSnackbar(message = message,setActionLabel(action))
        UndoDeleteActions(action,snack,onUndoClicked)
    }
    }

}
fun setActionLabel(action: Action):String{
    return if(action==Action.DELETE || action==Action.DELETE_ALL) "Undo" else "OK"
}
fun UndoDeleteActions(action: Action,snackbarResult: SnackbarResult,undoClicked:(Action)->Unit){
    if(snackbarResult==SnackbarResult.ActionPerformed&& (action==Action.DELETE_ALL || action== Action.DELETE)){
        undoClicked(Action.UNDO)
        }



}