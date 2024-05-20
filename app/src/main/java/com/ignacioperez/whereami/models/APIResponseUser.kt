package com.ignacioperez.whereami.models

import android.icu.text.IDNA

data class APIResponseUser (
    val info: IDNA.Info,
    val result: User
)
