package com.ignacioperez.whereami.models

data class CharacterResponse(
    val custom: Boolean,
    val id: Int,
    val name: String,
    val tainted: Boolean,
    val unlockable: Boolean,
    val wayToUnlock: String,
    val imageUrl: String
)