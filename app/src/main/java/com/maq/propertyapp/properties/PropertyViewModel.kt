package com.maq.propertyapp.properties

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.maq.propertyapp.util.Coroutines
import kotlinx.coroutines.Job


class PropertyViewModel(private val repository: PropertiesRepository) : ViewModel() {

    private lateinit var job: Job

    private val _properties = MutableLiveData<Property>()
    val properties : LiveData<Property>
                get() = _properties

    init{
        Log.i("View Model","View Model created")
    }

    //to keep track of lifetime of view model
    override fun onCleared() {
        super.onCleared()
        Log.i("View Model","View Model destroyed")
        if(::job.isInitialized) job.cancel()
    }

     fun getProperties(){

        job = Coroutines.ioThenMain(
                { repository.getProperties() },
         { _properties.value = it }
         )
    }





}