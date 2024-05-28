package com.ignacioperez.whereami.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ignacioperez.whereami.models.CardRune
import com.ignacioperez.whereami.models.ListCardRunes
import com.ignacioperez.whereami.models.ObjectChangeStats
import com.ignacioperez.whereami.retrofitInterface.RetrofitServiceFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CardRuneViewModel : ViewModel() {
    private val _selectedCardRune = MutableLiveData<CardRune>()
    val selectedCardRune: LiveData<CardRune> = _selectedCardRune

    private val _statsChangedByCardRune = MutableLiveData<ObjectChangeStats>()
    val statsChangedByCardRune: LiveData<ObjectChangeStats> = _statsChangedByCardRune

    private val _allCardsRunes = MutableLiveData<ListCardRunes>()
    val allCardsRunes: LiveData<ListCardRunes> = _allCardsRunes

    private var _responseError = MutableLiveData<Boolean>()
    val responseError: LiveData<Boolean> = _responseError

    fun onCardRuneClicked(cardRune: CardRune) {
        _selectedCardRune.value = cardRune
        loadStats(cardRune.id)
    }

    fun loadStats(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val service = RetrofitServiceFactory.getRetrofit()
            try {
                val result = service.getStatsModifiedByPickup(id)
                _statsChangedByCardRune.postValue(result)
                _responseError.postValue(false)
            } catch (e: Exception) {
                _responseError.postValue(true)
            }
        }
    }

    fun getAllCardsRunes() {
        viewModelScope.launch(Dispatchers.IO) {
            val service = RetrofitServiceFactory.getRetrofit()
            try {
                val result = service.getAllCardRunes()
                _allCardsRunes.postValue(result)
                _responseError.postValue(false)
            } catch (e: Exception) {
                _responseError.postValue(true)
            }
        }
    }

}