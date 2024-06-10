package com.ignacioperez.whereami.retrofitInterface

import com.ignacioperez.whereami.models.CardRune
import com.ignacioperez.whereami.models.CharacterResponse
import com.ignacioperez.whereami.models.Item
import com.ignacioperez.whereami.models.ListCardRunes
import com.ignacioperez.whereami.models.ListPills
import com.ignacioperez.whereami.models.ObjectChangeStatsList
import com.ignacioperez.whereami.models.ListTrinkets
import com.ignacioperez.whereami.models.NewCharacter
import com.ignacioperez.whereami.models.StatResponse
import com.ignacioperez.whereami.models.StatsModifiedCharacter
import com.ignacioperez.whereami.models.User
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface RetrofitService {

    @POST("users/create")
    suspend fun createUser(@Body user: User): User

    @POST("characters/new")
    suspend fun createCharacter(@Body character: NewCharacter)

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
    suspend fun getStatsChanges(@Path("id") id: Int): ObjectChangeStatsList

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

    @GET("trinkets/trinket_modifies/{id}")
    suspend fun getStatsChangedByTrinket(@Path("id") id: Int): ObjectChangeStatsList


    @GET("cards_runes/")
    suspend fun getAllCardRunes(): ListCardRunes

    @GET("pills/")
    suspend fun getAllPills(): ListPills

    @GET("pickups/pickup_modifies/{id}")
    suspend fun getStatsModifiedByPickup(@Path("id") id: Int): ObjectChangeStatsList

    @GET("users/getFavoriteItems/{id}")
    suspend fun getFavoriteItemsByUser(@Path("id") id: Int): List<Item>

    @GET("users/itemIsFavorite/{idItem}/{idUser}")
    suspend fun isItemFavorite(@Path("idItem") idItem: Int, @Path("idUser") idUser: Int): Boolean

    @GET("users/pickupIsFavorite/{idPickup}/{idUser}")
    suspend fun isPickupFavorite(
        @Path("idPickup") idPickup: Int,
        @Path("idUser") idUser: Int
    ): Boolean

    @POST("users/addItemToFavorite/{idItem}/{idUser}")
    suspend fun insertItemFavorite(@Path("idItem") idItem: Int, @Path("idUser") idUser: Int)

    @DELETE("users/deleteItemFavorite/{idUser}/{idItem}")
    suspend fun deleteItemFavorite(@Path("idUser") idUser: Int, @Path("idItem") idItem: Int)

    @POST("users/addPickupToFavorite/{idPickup}/{idUser}")
    suspend fun insertPickupFavorite(@Path("idPickup") idPickup: Int, @Path("idUser") idUser: Int)

    @DELETE("users/deletePickupFavorite/{idUser}/{idPickup}")
    suspend fun deletePickupFavorite(@Path("idUser") idUser: Int, @Path("idPickup") idPickup: Int)


    @GET("users/getFavoriteCardRune/{id}")
    suspend fun getFavoriteCardRunesByUser(@Path("id") id: Int): List<CardRune>

    @GET("users/getFavoritePill/{id}")
    suspend fun getFavoritePillsByUser(@Path("id") id: Int): ListPills

    @GET("characters/get_tainted_characters/")
    suspend fun getTaintedCharacters(): List<CharacterResponse>

    @GET("items/unlockable_items/")
    suspend fun getUnlockableItems(): List<Item>

    @GET("items/active_items/")
    suspend fun getActiveItems(): List<Item>

    @GET("pills/neutral_pills/")
    suspend fun getNeutralPills(): ListPills

    @GET("pills/negative_pills/")
    suspend fun getNegativePills(): ListPills

    @GET("pills/positive_pills/")
    suspend fun getPositivePills(): ListPills

    @GET("pills/unlockable_pills/")
    suspend fun getUnlockablePills(): ListPills

    @GET("trinkets/unlockable_trinkets/")
    suspend fun getUnlockableTrinkets(): ListTrinkets

    @GET("characters/get_last_imageUrl/")
    suspend fun getLastImage(): String

}
