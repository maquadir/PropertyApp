package com.maq.propertyapp.properties

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

//viewmodel factory class to create view models

@Suppress("UNCHECKED_CAST")
class PropertyViewModelFactory (
    private val repository: PropertiesRepository
) : ViewModelProvider.NewInstanceFactory(){

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PropertyViewModel(repository) as T
    }

}