package com.example.mynoteapp.Data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "NoteTable")
data  class Item(@PrimaryKey(autoGenerate = true) val id:Int=0, var title:String, var description:String, var priority: Priority, val locked: Locked?=null, val lockState:LockState,
                 val date: Calendar?
)