package com.master.germanlearningjetpack.room

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelFactory(var application: Application  , var myId:Int?):ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DatabaseViewModel(application  = application , myId = myId) as T
    }
}