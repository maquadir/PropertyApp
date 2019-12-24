package com.maq.propertyapp.properties

import com.maq.propertyapp.network.PropertiesApi
import com.maq.propertyapp.network.SafeApiRequest

class PropertiesRepository (
    private val api: PropertiesApi
) : SafeApiRequest() {

    suspend fun getProperties() = apiRequest { api.getProperties() }

}