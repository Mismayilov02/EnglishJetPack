package com.master.germanlearningjetpack.room

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class DatabaseRepostory( var application: Application) {
    var allData   = MutableLiveData<List<History>>()
    var myRoomDatabase :MyRoomDatabase
    init {
        myRoomDatabase = MyRoomDatabase.getDatabase(application)!!
        allData = MutableLiveData()
    }

    fun readAllData():MutableLiveData<List<History>>{
        return  allData
    }

    fun getAllDataByBaseWord(){
        val job:Job = CoroutineScope(Dispatchers.Main).launch {
            allData.value = myRoomDatabase.historyDao().readAllByBaseWord()
        }
    }

    fun getAllDataBnyMyId(myid:Int){
        val job:Job = CoroutineScope(Dispatchers.Main).launch {
            allData.value = myRoomDatabase.historyDao().readAllByMyId(myid)
        }
    }

    fun deleteBaseWord(id:Int){
        val job:Job = CoroutineScope(Dispatchers.Main).launch {
            myRoomDatabase.historyDao().deleteValueById(id)
            getAllDataByBaseWord()
        }
    }


    fun insertData(history: History, context: Context, myId: Int?){
        val job:Job = CoroutineScope(Dispatchers.Main).launch {
//            allData.value = myRoomDatabase.historyDao().readAll()
            myRoomDatabase.historyDao().insert(history)
            Toast.makeText(context , "Added Catagory" , Toast.LENGTH_SHORT).show()

            if (myId == null){
                getAllDataByBaseWord()
            }else{
                getAllDataBnyMyId(myId)
            }
        }
    }

}