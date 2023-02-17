package com.master.englishlearningjetpack.room

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class DatabaseViewModel(application: Application , myId: Int?):AndroidViewModel(application) {

    var getAllData = MutableLiveData<List<History>>()
      var repostory = DatabaseRepostory(application)

    init {
//        val  historyDao = MyRoomDatabase.getDatabase(application).historyDao()
//       getAllData()
       if (myId == null){
           getAllDataByBaseWord()
       }else{
           getAllDataById(myId)
       }
        getAllData = repostory.readAllData()
    }

//    fun addItem(history: History){
//        viewModelScope.launch(Dispatchers.IO) {
//            repostory.addValues(history)
//        }
//    }

//    fun getAllData(){
//        repostory.getAllData()
//    }

    fun getAllDataByBaseWord(){
        repostory.getAllDataByBaseWord()
    }

    fun getAllDataById(myId: Int){
        repostory.getAllDataBnyMyId(myId)
    }



    fun insertData(history: History, context:Context, myId: Int?){
        repostory.insertData(history = history , context)
        if (myId == null){
            getAllDataByBaseWord()
        }else{
            getAllDataById(myId)
        }
    }


}