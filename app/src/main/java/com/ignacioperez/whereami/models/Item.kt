package com.ignacioperez.whereami.models

data class Item(
    val id: Int = -1,
    val description: String = "",
    val charges: Int = -1,
    val unlockable: Boolean = false,
    val quote: String = "",
    val name: String = "",
    val wayToUnlock: String = "",
    val imageUrl: String = ""
)