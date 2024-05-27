package com.ignacioperez.whereami.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ignacioperez.whereami.models.CardRune

class CardRuneViewModel : ViewModel() {
    private val _selectedCardRune = MutableLiveData<CardRune>()
    val selectedCardRune: LiveData<CardRune> = _selectedCardRune

    
}