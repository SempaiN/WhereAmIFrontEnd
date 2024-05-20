package com.ignacioperez.whereami.models

data class Item(
    val id: Int,
    val description: String,
    val charges: Int,
    val unlockable: Boolean,
    val quote: String,
    val name: String,
    val wayToUnlock: String
)