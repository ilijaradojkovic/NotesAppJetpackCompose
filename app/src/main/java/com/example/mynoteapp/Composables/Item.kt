package com.example.mynoteapp.Composables

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mynoteapp.Data.LockState
import com.example.mynoteapp.Data.Priority
import com.example.mynoteapp.R
import com.example.mynoteapp.Room.Converter
import com.example.mynoteapp.ui.theme.ItemColor

import com.example.mynoteapp.ui.theme.TextColor
import com.example.mynoteapp.ui.theme.TitleColor
import java.util.*


@Preview
@Composable
fun elementPrewiew(){
    Element(title = "", description ="" , lockState = LockState.UNLOCKED, priority = Priority.LOW, id = 0, date = Calendar.getInstance(),{})


}
@Composable
fun Element(
    title: String, description: String,lockState: LockState, priority: Priority, id: Int,date:Calendar,
    ItemClicked:()->Unit) {

    Card(Modifier.border(1.dp, Color.DarkGray,shape = RoundedCornerShape(10.dp)),shape = RoundedCornerShape(10.dp),backgroundColor = MaterialTheme.colors.ItemColor) {


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Max)
                .padding(9.dp)
                .clickable { ItemClicked() }
        ) {

            Column(
                modifier = Modifier
                    .height(IntrinsicSize.Max)
                    .weight(4f)
            ) {
                Text(title, modifier = Modifier
                    .fillMaxWidth()
                    .weight(2f),fontSize =20.sp,color=MaterialTheme.colors.TitleColor,maxLines = 1,fontWeight = FontWeight.Bold ,overflow = TextOverflow.Ellipsis)
                Text(description, modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),fontSize =13.sp,color=MaterialTheme.colors.TextColor,maxLines = 1 ,overflow = TextOverflow.Ellipsis)
            }

            Column(
                modifier = Modifier
                    .weight(2f)
                    .fillMaxHeight()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f), contentAlignment = Alignment.TopEnd
                ) {

                    Canvas(Modifier.padding(10.dp)) {
                        drawCircle(priority.color, 20f)
                    }
                }
                if(lockState==LockState.LOCKED)
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f), contentAlignment = Alignment.CenterEnd
                    ) {
                        Icon(painterResource(id = R.drawable.lock), "")
                    }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f), contentAlignment = Alignment.CenterEnd
                ) {
                    Text(Converter.fromDate(date),color=MaterialTheme.colors.TextColor,modifier = Modifier.wrapContentWidth())
                }
            }


        }
    }
}



