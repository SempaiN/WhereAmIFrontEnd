package com.ignacioperez.whereami.retrofitInterface

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object RetrofitServiceFactory {
    fun getRetrofit(): RetrofitService {
        return Retrofit.Builder()
            .baseUrl("http://192.168.0.151:8080/tboi/") //Casa
//            .baseUrl("http://192.168.130.151:8080/tboi/")  //Móvil
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RetrofitService::class.java)


    }
}