package com.ignacioperez.whereami.models

data class Pill(
    val id: Int,
    val effect: String,
    val imageUrl: String,
    val name: String,
    val polarity: Int
)