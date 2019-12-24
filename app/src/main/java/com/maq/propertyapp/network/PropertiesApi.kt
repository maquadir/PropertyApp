package com.maq.propertyapp.network

import com.maq.propertyapp.properties.Property
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

const val BASE_URl = "https://demo7442132.mockable.io/test/"

//to read data from JSON url

interface PropertiesApi {


      @GET("properties")
      suspend fun getProperties() : Response<Property>

    companion object  {

        operator fun invoke(): PropertiesApi {
            return Retrofit.Builder()
                .baseUrl(BASE_URl)
                .addConverterFactory(MoshiConverterFactory.create())
                .build().create(PropertiesApi::class.java)
        }
    }
}