package com.ignacioperez.whereami.models

data class CharacterResponse(
    val id: Int = -1,
    val name: String = "",
    val unlockable: Boolean = false,
    val custom: Boolean = false,
    val wayToUnlock: String = "",
    val tainted: Boolean = false,
    val imageUrl: String = "",
    val transitionImage: String = ""
)