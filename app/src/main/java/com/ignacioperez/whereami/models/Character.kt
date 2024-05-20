package com.ignacioperez.whereami.models

data class Character(
    val custom: Boolean,
    val id: Int,
    val name: String,
    val tainted: Boolean,
    val unlockable: Boolean,
    val wayToUnlock: Any
)