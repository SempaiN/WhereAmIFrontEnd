package com.ignacioperez.whereami.models

data class Item(
    val charges: Int,
    val description: String,
    val id: Int,
    val name: String,
    val quote: String,
    val unlockable: Boolean,
    val wayToUnlock: String
)