package com.maq.propertyapp

import android.util.Log
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {



    init{
        Log.i("View Model","View Model created")
    }

    //to keep track of lifetime of view model
    override fun onCleared() {
        super.onCleared()
        Log.i("View Model","View Model destroyed")
    }



}