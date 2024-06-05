package com.ignacioperez.whereami.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ignacioperez.whereami.models.CardRune
import com.ignacioperez.whereami.models.Item
import com.ignacioperez.whereami.models.ListCardRunes
import com.ignacioperez.whereami.models.ObjectChangeStatsList
import com.ignacioperez.whereami.models.User
import com.ignacioperez.whereami.retrofitInterface.RetrofitServiceFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CardRuneViewModel : ViewModel() {
    private val _selectedCardRune = MutableLiveData<CardRune>()
    val selectedCardRune: LiveData<CardRune> = _selectedCardRune

    private val _statsChangedByCardRune = MutableLiveData<ObjectChangeStatsList>()
    val statsChangedByCardRune: LiveData<ObjectChangeStatsList> = _statsChangedByCardRune

    private val _allCardsRunes = MutableLiveData<ListCardRunes>()
    val allCardsRunes: LiveData<ListCardRunes> = _allCardsRunes

    private var _responseError = MutableLiveData<Boolean>()
    val responseError: LiveData<Boolean> = _responseError

    private val _showCardRuneDetails = MutableLiveData<Boolean>()
    val showCardRuneDetails: LiveData<Boolean> = _showCardRuneDetails

    private val _isCardRuneSelectedFavorite = MutableLiveData<Boolean>()
    val isCardRuneSelectedFavorite: LiveData<Boolean> = _isCardRuneSelectedFavorite

    fun checkCardRuneFavorite(cardRune: CardRune, user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            val service = RetrofitServiceFactory.getRetrofit()
            try {
                val result = service.isPickupFavorite(cardRune.id, user.id)
                _isCardRuneSelectedFavorite.postValue(result)
            } catch (e: Exception) {
                Log.i("Error", e.toString())
                _isCardRuneSelectedFavorite.postValue(false)
            }
        }
    }

    fun onCardRuneClicked(cardRune: CardRune) {
        _selectedCardRune.value = cardRune
        loadStats(cardRune.id)
        showCardRuneDetailsAlertDialog()
    }

    fun clearSelectedCharacter() {
        _selectedCardRune.value = CardRune()
    }

    fun showCardRuneDetailsAlertDialog() {
        _showCardRuneDetails.value = true
    }

    fun hideCardRuneDetailsAlertDialog() {
        _showCardRuneDetails.value = false
    }

    fun loadStats(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val service = RetrofitServiceFactory.getRetrofit()
            try {
                val result = service.getStatsModifiedByPickup(id)
                Log.i("stats", result.toString())
                _statsChangedByCardRune.postValue(result)
                _responseError.postValue(false)
            } catch (e: Exception) {
                Log.i("Error", e.toString())
                _responseError.postValue(true)
            }
        }
    }

    fun insertCardRuneFavorite(cardRune: CardRune, user: User,userViewModel: UserViewModel) {
        viewModelScope.launch(Dispatchers.IO) {
            val service = RetrofitServiceFactory.getRetrofit()
            try {
                service.insertPickupFavorite(cardRune.id, user.id)
                checkCardRuneFavorite(cardRune, user)
                userViewModel.getFavoriteCardRunes(user)
            } catch (e: Exception) {
                Log.i("--", e.message.toString())
            }
        }
    }

    fun deleteCardRuneFavorite(cardRune: CardRune, user: User,userViewModel: UserViewModel) {
        viewModelScope.launch(Dispatchers.IO) {
            val service = RetrofitServiceFactory.getRetrofit()
            try {
                service.deletePickupFavorite(user.id, cardRune.id)
                checkCardRuneFavorite(cardRune, user)
                userViewModel.getFavoriteCardRunes(user)

            } catch (e: Exception) {
                Log.i("--", e.message.toString())
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