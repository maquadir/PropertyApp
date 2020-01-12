package com.maq.propertyapp.properties

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maq.propertyapp.util.Coroutines
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


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

    //coroutines - get data from repository
    //fetching properties in iO thread and making changes to live data in main thread
     fun getProperties(){


            job =
                viewModelScope.launch {

                    Coroutines.ioThenMain(
                        { repository.getProperties() },
                        { _properties.value = it }
                    )
                }


    }

//    suspend fun getProperties(){
//        val properties = repository.getProperties()
//        _properties.value = properties
//    }





}