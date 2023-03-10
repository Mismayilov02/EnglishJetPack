package com.master.englishlearningjetpack.room

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface HistoryDao {
    @Insert
    suspend fun insert(history: History)

    @Query("select * from history")
    suspend fun readAll() : List<History>

    @Query("select * from history where myId =:myid ")
    suspend fun readAllByMyId(myid:Int) : List<History>


    @Query("select * from history where myId =0 order by id desc")
    suspend fun readAllByBaseWord() : List<History>

    @Query("DELETE FROM history WHERE id = :id or myId = :id")
    suspend fun deleteValueById(id: Int)
}