package com.master.englishlearningjetpack.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [History::class] , version = 1 , exportSchema = false)
abstract class MyRoomDatabase :RoomDatabase(){
    abstract fun historyDao():HistoryDao

    companion object{
        @Volatile
        var INSTANCE:MyRoomDatabase? = null

        fun getDatabase(context: Context):MyRoomDatabase{
            if (INSTANCE == null){
                synchronized(MyRoomDatabase::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        MyRoomDatabase::class.java,
                        "englishlearning.sqlite")
                        .createFromAsset("englishlearning.sqlite")
                        .build()

                }
            }
            return INSTANCE!!
        }
    }


}