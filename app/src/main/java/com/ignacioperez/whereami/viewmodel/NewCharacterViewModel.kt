package com.ignacioperez.whereami.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ignacioperez.whereami.models.CardRune
import com.ignacioperez.whereami.models.Item
import com.ignacioperez.whereami.models.ObjectChangeStatsList
import com.ignacioperez.whereami.models.Pill
import com.ignacioperez.whereami.models.Stat
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

    private val _healthStat = MutableLiveData<Double>()
    val healthStat: LiveData<Double> = _healthStat

    private val _damageStat = MutableLiveData<Double>()
    val damageStat: LiveData<Double> = _damageStat

    private val _tearsStat = MutableLiveData<Double>()
    val tearsStat: LiveData<Double> = _tearsStat

    private val _shotSpeedStat = MutableLiveData<Double>()
    val shotSpeedStat: LiveData<Double> = _shotSpeedStat

    private val _rangeStat = MutableLiveData<Double>()
    val rangeStat: LiveData<Double> = _rangeStat

    private val _luckStat = MutableLiveData<Double>()
    val luckStat: LiveData<Double> = _luckStat

    private val _speedStat = MutableLiveData<Double>()
    val speedStat: LiveData<Double> = _speedStat



    fun setHealthStat(value: Double) {
        _healthStat.value = value
    }


    fun setDamageStat(value: Double) {
        _damageStat.value = value
    }

    fun setTearsStat(value: Double) {
        _tearsStat.value = value
    }

    fun setShotSpeedStat(value: Double) {
        _shotSpeedStat.value = value
    }

    fun setRangeStat(value: Double) {
        _rangeStat.value = value
    }

    fun setLuckStat(value: Double) {
        _luckStat.value = value
    }

    fun setSpeedStat(value: Double) {
        _speedStat.value = value
    }

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

    fun removeTrinketFromList() {
        _trinketNewCharacter.value = null
    }

    fun removeCardRuneFromList() {
        _cardRuneNewCharacter.value = null
    }


}