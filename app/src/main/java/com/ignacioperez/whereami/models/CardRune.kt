package com.ignacioperez.whereami.models

data class CardRune(
    val id: Int,
    val description: String,
    val imageUrl: String,
    val message: String,
    val name: String,
    val unlockable: Boolean,
    val wayToUnlock: String
)