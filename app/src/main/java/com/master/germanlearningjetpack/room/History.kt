package com.master.germanlearningjetpack.room

import androidx.annotation.Nullable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history")
data  class History(@PrimaryKey(autoGenerate = true)
                    @ColumnInfo(name = "id")
                    var id:Int,
                    @Nullable
                    @ColumnInfo(name = "az")
                    var az: String?,
                    @Nullable
                    @ColumnInfo(name = "en")
                    var en: String?,
                    @Nullable
                    @ColumnInfo(name = "name")
                    var name:String?,
                    @ColumnInfo(name = "myId")
                    var myId:Int) {


}