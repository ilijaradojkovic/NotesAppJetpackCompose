package com.example.mynoteapp.Data

import kotlinx.coroutines.flow.Flow

sealed class Request{
    object Loading:Request()
    data class  Success(val data: List<Item>):Request()
    data class Error(val errorMsg:String):Request()
}
