package com.example.mynoteapp.ui.theme


import androidx.compose.material.Colors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.mynoteapp.R

val Primary = Color(0xff7e56c1)
val Secondary = Color(0xff039be5)
val Primary_Light = Color(0xffb084f4)
val Primary_Dark = Color(0xff4d2b90)
val Secondary_Dark = Color(0xff006db3)
val Secondary_Light = Color(0xff63ccff)
val onPrimaryText=Color(0xFF000000)
val onSecondaryText=Color(0xFFFFFFFF)
val itemColor_Dark=Color(0xffE6E6E6)
val itemColor_Light=Color(0xffCECECE)
val DescriptionColor=Color.DarkGray
val contentColorNoData_Light=Color(0xff727272)
val contentColorNoData_Dark=Color(0xffB9B9B9)
val Colors.TopAppBarColor:Color
@Composable
get()=if(isLight) Primary else Secondary


val Colors.ItemColor:Color
@Composable
get() = if(isLight) itemColor_Light else itemColor_Dark

val Colors.TitleColor:Color
@Composable
get()=if(isLight) onPrimaryText else onSecondaryText

val Colors.TextColor:Color
    @Composable
    get()=if(isLight) DescriptionColor else Color.White

val Colors.NotDataColor:Color
@Composable
get()=if(isLight) contentColorNoData_Light else contentColorNoData_Dark