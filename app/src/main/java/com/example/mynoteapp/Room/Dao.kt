package com.example.mynoteapp.Room

import androidx.room.*
import androidx.room.Dao
import com.example.mynoteapp.Data.Item
import kotlinx.coroutines.flow.Flow


@Dao
interface Dao {

@Query("SELECT * FROM NoteTable")
fun getAllData(): Flow<List<Item>>

@Query("SELECT * FROM notetable WHERE title LIKE :Text OR description LIKE :Text")
fun searchData(Text:String):Flow<List<Item>>

@Query("SELECT * FROM NoteTable WHERE id=:NoteId")
fun getSpesificData(NoteId:Int):Flow<Item>

@Query("SELECT * FROM NoteTable ORDER BY CASE WHEN priority LIKE 'H%' THEN 1 WHEN priority LIKE 'M%' THEN 2 ELSE 3 END   ")
fun orderByHighPriority():Flow<List<Item>>


    @Query("SELECT * FROM NoteTable ORDER BY CASE WHEN priority LIKE 'L%' THEN 1 WHEN priority LIKE 'M%' THEN 2 ELSE 3 END   ")
    fun orderByLowPriority():Flow<List<Item>>

    @Query("SELECT * FROM NoteTable ORDER BY CASE WHEN priority LIKE 'M%' THEN 1 WHEN priority LIKE 'L%' THEN 2 ELSE 3 END   ")
    fun orderByMediumPriority():Flow<List<Item>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
suspend fun insertData(item: Item)

@Update
suspend fun  update(item: Item)

@Delete
suspend fun  delete(item: Item)

@Query("DELETE FROM NoteTable")
suspend fun  deleteAll()

}