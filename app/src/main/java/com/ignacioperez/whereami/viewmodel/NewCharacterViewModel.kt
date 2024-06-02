package com.ignacioperez.whereami.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ignacioperez.whereami.models.CardRune
import com.ignacioperez.whereami.models.Item
import com.ignacioperez.whereami.models.ObjectChangeStatsList
import com.ignacioperez.whereami.models.Pill
import com.ignacioperez.whereami.models.Trinket

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

    private val _statsChangedByItems = MutableLiveData<ObjectChangeStatsList>()
    val statsChangedByItems: LiveData<ObjectChangeStatsList> = _statsChangedByItems

    fun setNameNewCharacter(name: String) {
        _newCharacterName.value = name
    }

    fun setTrinketNewCharacter(trinket: Trinket) {
        _trinketNewCharacter.value = trinket
    }

    fun setTrinketNull() {
        _trinketNewCharacter.value = null
    }

    fun setCardRuneCharacter(cardRune: CardRune) {
        _cardRuneNewCharacter.value = cardRune
    }

    fun setPillCharacter(pill: Pill) {
        _pillNewCharacter.value = pill
    }

    fun setCardRuneCharacterNull() {
        _cardRuneNewCharacter.value = null
    }

    fun setPillCharacterNull() {
        _pillNewCharacter.value = null
    }

    //Referenced project code ListIgnacio
    fun addNewItemToList(item: Item) {
        val currentList = _listItemsNewCharacter.value ?: emptyList()
        _listItemsNewCharacter.value = currentList + item

    }

    fun removeItemFromList(item: Item) {
        val updatedList = listItemsNewCharacter.value?.toMutableList()
        if (updatedList?.size == 1) {
            updatedList.clear()
        } else {
            updatedList?.remove(item)
        }
        _listItemsNewCharacter.value = updatedList
    }


}