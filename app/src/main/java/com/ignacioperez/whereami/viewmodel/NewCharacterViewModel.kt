package com.ignacioperez.whereami.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ignacioperez.whereami.models.CardRune
import com.ignacioperez.whereami.models.Item
import com.ignacioperez.whereami.models.Pill
import com.ignacioperez.whereami.models.Trinket
import okhttp3.internal.cookieToString

class NewCharacterViewModel : ViewModel() {
    private val _newCharacterName = MutableLiveData<String>()
    var newCharacterName: LiveData<String> = _newCharacterName

    private val _listItemsNewCharacter = MutableLiveData<List<Item>>()
    val listItemsNewCharacter: LiveData<List<Item>> = _listItemsNewCharacter

    private val _trinketNewCharacter = MutableLiveData<Trinket>()
    val trinketNewCharacter: LiveData<Trinket> = _trinketNewCharacter

    private val _cardRuneNewCharacter = MutableLiveData<CardRune>()
    val cardRuneNewCharacter: LiveData<CardRune> = _cardRuneNewCharacter

    private val _pillNewCharacter = MutableLiveData<Pill>()
    val pillNewCharacter: LiveData<Pill> = _pillNewCharacter

    fun setNameNewCharacter(name: String) {
        _newCharacterName.value = name
    }

    //Referenced project code ListIgnacio
    fun addNewItemToList(item: Item) {
        val currentList = _listItemsNewCharacter.value ?: emptyList()
        _listItemsNewCharacter.value = currentList + item

    }
}