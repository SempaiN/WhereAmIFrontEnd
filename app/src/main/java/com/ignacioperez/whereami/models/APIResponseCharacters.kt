package com.ignacioperez.whereami.models

import android.icu.text.IDNA.Info

data class APIResponseCharacters(
    val info: Info,
    val characters: List<Character>
)