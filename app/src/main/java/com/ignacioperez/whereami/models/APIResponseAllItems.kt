package com.ignacioperez.whereami.models

import android.icu.text.IDNA.Info

data class
APIResponseAllItems(
    val info: Info,
    val items: List<Item>
)
