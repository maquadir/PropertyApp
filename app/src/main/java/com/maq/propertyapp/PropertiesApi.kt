package com.maq.propertyapp

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

const val BASE_URl = "https://demo7442132.mockable.io/test/"

interface PropertiesApi {


      @GET("properties")
      suspend fun getProperties() : Response<Property>

    companion object  {

        fun invoke(): PropertiesApi {
            return Retrofit.Builder()
                .baseUrl(BASE_URl)
                .addConverterFactory(MoshiConverterFactory.create())
                .build().create(PropertiesApi::class.java)
        }
    }
}