package com.example.mynoteapp.Dagger

import android.content.Context

import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mynoteapp.Data.LockState
import com.example.mynoteapp.Data.Locked
import com.example.mynoteapp.R
import com.example.mynoteapp.Room.Dao
import com.example.mynoteapp.Room.Database
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.time.LocalDate
import java.util.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Module {

    @Singleton
    @Provides
    fun getDatabase(@ApplicationContext context: Context)=
        Room.databaseBuilder(context,Database::class.java,"MyDatabase").build()


    @Singleton
    @Provides
    fun getDao(database: Database)=
         database.GetDao()



    @Singleton
    fun getLockedState(state:Int)=if(state==1) LockState.LOCKED else LockState.UNLOCKED

    @Singleton
    fun getDate(date: Calendar)= "${date.get(Calendar.DAY_OF_MONTH)}/${date.get(Calendar.MONTH)}/${date.get(Calendar.YEAR)}"
}