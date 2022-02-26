package com.example.mynoteapp.Room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.mynoteapp.Data.Item


@Database(entities = [Item::class],version = 1)
@TypeConverters( Converter::class )
abstract class Database:RoomDatabase() {

    abstract fun GetDao():Dao
}