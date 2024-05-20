package com.ignacioperez.whereami.models

import android.icu.text.IDNA.Info

data class LastUser(
    val info: Info,
    val lastUser: Int
)
