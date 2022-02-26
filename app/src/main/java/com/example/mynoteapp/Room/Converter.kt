package com.example.mynoteapp.Room

import android.util.Log
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.mynoteapp.Data.Locked
import java.util.*

object Converter {
    @TypeConverter
    fun fromLoceked(locked: Locked?):String{
        return locked?.password ?: ""
    }
    @TypeConverter
    fun toLocked(locked:String):Locked{
        return Locked(locked)
    }

    @TypeConverter
    fun fromDate(date:Calendar):String{
        return "${date.get(Calendar.DAY_OF_MONTH)}/${date.get(Calendar.MONTH)}/${date.get(Calendar.YEAR)}"
    }

    @TypeConverter
    fun toDate(date:String):Calendar{
        val niz=date.split('/')

       val cal= Calendar.getInstance()
           cal.set(niz[2].toInt(),niz[1].toInt(),niz[0].toInt())

        return cal
    }
}