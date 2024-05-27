package com.ignacioperez.whereami.models

data class Trinket(
    val description: String = "",
    val id: Int = -1,
    val name: String = "",
    val quote: String = "",
    val unlockable: Boolean = false,
    val wayToUnlock: String = "",
    val imageUrl: String = ""
)