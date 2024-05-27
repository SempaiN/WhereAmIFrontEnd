package com.ignacioperez.whereami.retrofitInterface

import com.ignacioperez.whereami.models.APIResponseItem
import com.ignacioperez.whereami.models.CharacterResponse
import com.ignacioperez.whereami.models.Item
import com.ignacioperez.whereami.models.ListCardRunes
import com.ignacioperez.whereami.models.ListPickups
import com.ignacioperez.whereami.models.ListPills
import com.ignacioperez.whereami.models.ObjectChangeStats
import com.ignacioperez.whereami.models.ListTrinkets
import com.ignacioperez.whereami.models.StatResponse
import com.ignacioperez.whereami.models.StatsModifiedCharacter
import com.ignacioperez.whereami.models.Trinket
import com.ignacioperez.whereami.models.User
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface RetrofitService {

    @GET("item/{id}")
    suspend fun getItemById(@Path("id") id: Int): APIResponseItem

    @POST("users/create")
    suspend fun createUser(@Body user: User): User

    @GET("users/last")
    suspend fun getLastUser(): User

    @GET("items/")
    suspend fun getAllItems(): List<Item>

    @GET("characters/")
    suspend fun getAllDefaultCharacters(): List<CharacterResponse>

    @GET("users/get_characters_user/{id}")
    suspend fun getCharactersByUser(@Path("id") id: Int): List<CharacterResponse>

    @GET("users/get/{email}")
    suspend fun getUserByEmail(
        @Path("email") email: String,
    ): User

    @GET("items/get_stats_changes/{id}")
    suspend fun getStatsChanges(@Path("id") id: Int): ObjectChangeStats

    @GET("characters/{id}")
    suspend fun getCharacterById(@Path("id") id: Int): CharacterResponse

    @GET("characters/get_item_character/{id}")
    suspend fun getItemsByCharacter(@Path("id") id: Int): List<Item>

    @GET("characters/stats_base/{id}")
    suspend fun getStatsBase(@Path("id") id: Int): StatResponse

    @GET("characters/stats_modified/{id}")
    suspend fun getStatsModified(@Path("id") id: Int): StatsModifiedCharacter

    @GET("trinkets/")
    suspend fun getAllTrinkets(): ListTrinkets

    @GET("trinkets/{id}")
    suspend fun getTrinket(@Path("id") id: Int): Trinket

    @GET("trinkets/trinket_modifies/{id}")
    suspend fun getStatsChangedByTrinket(@Path("id") id: Int): ObjectChangeStats

    @GET("pickups/")
    suspend fun getAllPickups(): ListPickups

    @GET("cards_runes/")
    suspend fun getAllCardRunes(): ListCardRunes

    @GET("pills/")
    suspend fun getAllPills(): ListPills

}