package com.example.mynoteapp.VIewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynoteapp.Data.*
import com.example.mynoteapp.Repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class NoteVIewModel  @Inject constructor(private val noteRepository: NoteRepository):ViewModel(){


    var action = MutableStateFlow(Action.NONE)
    private var _allData= MutableStateFlow<Request>(Request.Loading)
    val  allData =_allData

    private  val _searchData = MutableStateFlow<Request>(Request.Loading)
    val searchData =_searchData


    public fun getSearchData(searchString:String){
        _searchData.value=Request.Loading
        try {
            viewModelScope.launch {
                noteRepository.getSearchData("%$searchString%").collect {
                    _searchData.value = Request.Success(it)
                }
            }
        }
        catch (e:Exception){
            _searchData.value=Request.Error(e.message?:"error")
        }
    }
    private var _allDataOrederedByHighPrioirty= MutableStateFlow<Request>(Request.Loading)
    val allDataOrederedByHighPrioirty =_allDataOrederedByHighPrioirty

    private var _allDataOrederedByMediumPrioirty= MutableStateFlow<Request>(Request.Loading)
    val allDataOrederedByMediumPrioirty =_allDataOrederedByMediumPrioirty

    private var _allDataOrederedByLowPrioirty= MutableStateFlow<Request>(Request.Loading)
    val allDataOrederedByLowPrioirty =_allDataOrederedByLowPrioirty

    fun getDataByPrioirtyLow(){
        _allDataOrederedByLowPrioirty.value=Request.Loading
        try{
        viewModelScope.launch (Dispatchers.IO){

            noteRepository.getDataOrderedByLowPrioirty().collect {
                _allDataOrederedByLowPrioirty.value=Request.Success(it)
            }
        }
        }
        catch (e:Exception){
            _allDataOrederedByLowPrioirty.value=Request.Error(e.toString())
        }
    }

    fun getDataByPrioirtyHigh(){
        _allDataOrederedByHighPrioirty.value=Request.Loading
        try{
            viewModelScope.launch (Dispatchers.IO){

                noteRepository.getDataOrderedByHighPrioirty().collect {
                    _allDataOrederedByHighPrioirty.value=Request.Success(it)
                }
            }
        }
        catch (e:Exception){
            _allDataOrederedByHighPrioirty.value=Request.Error(e.toString())
        }
    }
    fun getDataByPrioirtyMedium(){
        _allDataOrederedByMediumPrioirty.value=Request.Loading
        try{
            viewModelScope.launch (Dispatchers.IO){

                noteRepository.getDataOrderedByMediumPrioirty().collect {
                    _allDataOrederedByMediumPrioirty.value=Request.Success(it)
                }
            }
        }
        catch (e:Exception){
            _allDataOrederedByMediumPrioirty.value=Request.Error(e.toString())
        }
    }
     fun getAllData(){
         _allData.value=Request.Loading
         try {
             viewModelScope.launch {

                 noteRepository.getAllData().collect {

                     _allData.value = Request.Success(it)
                 }
             }
         }
         catch(e:Exception ){
             _allData.value=Request.Error("Doslo je do greske ${e.toString()}")
         }

    }


    private var _spesificData = MutableStateFlow<Item>(Item(
        title = "",
        description = "",
        priority = Priority.NONE,
        locked = null,
        lockState = LockState.UNLOCKED,
        date = null))
    val spesificData = _spesificData

//Item note fields
    var title = _spesificData.value.title
    var id =_spesificData.value.id
    var description= _spesificData.value.description
    var date=_spesificData.value.date
    var lockState=_spesificData.value.lockState
    var locked=_spesificData.value.locked
    var priority=_spesificData.value.priority
    val password=locked?.password?: ""

//-------------------------------------
    fun getSpecificNote(id:Int){
        viewModelScope.launch(Dispatchers.IO) {
            noteRepository.getSpesificData(id).collect {
                _spesificData.value=it
            }
        }
    }

     fun  insertData(item: Item){
        viewModelScope.launch {
            noteRepository.insertData(item)
        }
    }
     fun  deleteData(item: Item){

        viewModelScope.launch {
            noteRepository.delete(item)
        }
    }
     fun  deleteAll(){
        viewModelScope.launch {

            noteRepository.deleteAll()
        }
    }
     fun  updateData(item: Item){
        viewModelScope.launch {
            noteRepository.updateData(item)
        }
    }

    fun handleAction(action: Action){

        when(action){
            Action.ADD->{saveData()}
            Action.DELETE->{readyItemDataFroDelete()}
            Action.DELETE_ALL->{deleteAll()}
            Action.UPDATE->{CreateAndUpdate()}
            Action.UNDO->{saveData()}
            else->{}
        }
        this.action.value=Action.NONE

    }
    fun changeAction(action:Action){
        this.action.value=action;
    }

    fun updateFileds(
        id:Int,
        title: String,
        description: String,
        priority: Priority,
        calendar: Calendar,
        lockState: LockState,
        locked: Locked? = null
    ){
        this.id=id
        this.title=title
        this.description=description
        this.priority=priority
        this.date=calendar
        this.lockState=lockState
        this.locked=locked



    }
    fun updateFileds(
item: Item
    ){
        this.id=item.id
        this.title=item.title
        this.description=item.description
        this.priority=item.priority
        this.date=item.date
        this.lockState=item.lockState
        this.locked=item.locked



    }
    fun readyItemDataFroDelete(){
        viewModelScope.launch (Dispatchers.IO){

            val item=Item(
                id=id,
                title = title,
                description = description,
                priority = priority,
                locked = Locked(password),
                lockState = lockState,
                date = date)

            deleteData(item)
        }

    }

    fun saveData(

    ){

        viewModelScope.launch (Dispatchers.IO){

            val item=Item(
                title = title,
                description = description,
                priority = priority,
                locked = locked,
                lockState = lockState,
                date = date)

            insertData(item)
        }

    }
    fun CreateAndUpdate(){
        viewModelScope.launch (Dispatchers.IO){

            val item=Item(spesificData.value.id,
                title = title,
                description = description,
                priority = priority,
                locked = locked,
                lockState = lockState,
                date = date)

            updateData(item)
        }
    }

    fun validateFields(title: String,description: String):Boolean{
        return title.isNotEmpty() && description.isNotEmpty()
    }


}