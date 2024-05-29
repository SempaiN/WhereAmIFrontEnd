package com.ignacioperez.whereami.models

data class Pill(
    val effect: String = "",
    val id: Int = -1,
    val imageUrl: String = "",
    val name: String = "",
    val polarity: Int = -1,
    val unlockable: Boolean = false,
    val wayToUnlock: String = ""
)