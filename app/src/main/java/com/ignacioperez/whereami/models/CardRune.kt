package com.ignacioperez.whereami.models

data class CardRune(
    val id: Int = -1,
    val description: String = "",
    val imageUrl: String = "",
    val message: String = "",
    val name: String = "",
    val unlockable: Boolean = false,
    val wayToUnlock: String = ""
)