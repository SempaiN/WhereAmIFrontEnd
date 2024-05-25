package com.ignacioperez.whereami.models

data class CharacterResponse(
    val custom: Boolean = false,
    val id: Int = -1,
    val name: String = "",
    val tainted: Boolean = false,
    val unlockable: Boolean = false,
    val wayToUnlock: String = "",
    val imageUrl: String = "",
    val transitionImage: String = ""
)