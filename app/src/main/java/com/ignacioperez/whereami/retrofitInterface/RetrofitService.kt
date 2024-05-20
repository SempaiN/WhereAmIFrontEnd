package com.ignacioperez.whereami.retrofitInterface

import com.ignacioperez.whereami.models.APIResponseAllItems
import com.ignacioperez.whereami.models.APIResponseItem
import com.ignacioperez.whereami.models.APIResponseUser
import com.ignacioperez.whereami.models.User
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface RetrofitService {

    @GET("item/{id}")
    suspend fun getItemById(@Path("id") id: Int): APIResponseItem

    @POST("users/create")
    suspend fun createUser(@Body user: User): APIResponseUser

    @GET("users/last")
    suspend fun getLastUser(): User

    @GET("item/")
    suspend fun getAllItems(): APIResponseAllItems

    @GET("ch")
}