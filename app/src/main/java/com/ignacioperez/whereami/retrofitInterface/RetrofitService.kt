package com.ignacioperez.whereami.retrofitInterface

import com.ignacioperez.whereami.models.APIResponseAllItems
import com.ignacioperez.whereami.models.APIResponseCharacters
import com.ignacioperez.whereami.models.APIResponseItem
import com.ignacioperez.whereami.models.APIResponseUser
import com.ignacioperez.whereami.models.Item
import com.ignacioperez.whereami.models.ItemChangeStats
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
    suspend fun getLastUser(): APIResponseUser

    @GET("items/")
    suspend fun getAllItems(): List<Item>

    @GET("characters/")
    suspend fun getAllDefaultCharacters(): APIResponseCharacters

    @GET("users/get_characters_user/{id}")
    suspend fun getCharactersByUser(@Path("id") id: Int): APIResponseCharacters

    @GET("users/get/{email}")
    suspend fun getUserByEmail(
        @Path("email") email: String,
    ): APIResponseUser

    @GET("items/get_stats_changes/{id}")
    suspend fun getStatsChanges(@Path("id") id: Int):ItemChangeStats
}