package com.ignacioperez.whereami.models

data class NewCharacter(
    val custom: Boolean,
    val imageUrl: String,
    val name: String,
    val tainted: Boolean,
    val transitionImage: String,
    val unlockable: Boolean,
    val wayToUnlock: String
)