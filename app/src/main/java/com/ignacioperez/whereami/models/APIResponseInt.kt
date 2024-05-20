package com.ignacioperez.whereami.models

import android.icu.text.IDNA.Info

data class APIResponseInt(
    val info: Info,
    val result: Int
)
