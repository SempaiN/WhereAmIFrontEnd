package com.ignacioperez.whereami.models

import android.icu.text.IDNA.Info

data class APIResponseItem(
    val info: Info,
    val result: Item
)
