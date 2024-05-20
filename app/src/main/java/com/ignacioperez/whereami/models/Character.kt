package com.ignacioperez.whereami.models

data class Character(
    val id: Int,
    val name: String,
    val unlockable: Boolean,
    val custom: Boolean,
    val wayToUnlock: Any,
    val tainted: Boolean,
)