package com.ignacioperez.whereami.models

data class Pickup(
    val id: Int,
    val imageUrl: String,
    val name: String,
    val unlockable: Boolean,
    val wayToUnlock: String
)