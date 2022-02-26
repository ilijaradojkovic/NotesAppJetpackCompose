package com.example.mynoteapp.Repository

import com.example.mynoteapp.Data.Item
import com.example.mynoteapp.Room.Dao
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ViewModelScoped
class NoteRepository @Inject constructor(private val  dao: Dao)  {

    fun getAllData(): Flow<List<Item>> = dao.getAllData()
    fun getSpesificData(NoteId:Int):Flow<Item> = dao.getSpesificData(NoteId)
    fun getSearchData(Text:String):Flow<List<Item>> =dao.searchData(Text)

    fun getDataOrderedByHighPrioirty() =dao.orderByHighPriority()
    fun getDataOrderedByLowPrioirty() =dao.orderByLowPriority()
    fun getDataOrderedByMediumPrioirty() =dao.orderByMediumPriority()
   suspend fun insertData(item: Item) = dao.insertData(item)

    suspend fun  updateData(item: Item) = dao.update(item)
    suspend fun  delete(item: Item) = dao.delete(item)
    suspend fun  deleteAll() = dao.deleteAll()
}