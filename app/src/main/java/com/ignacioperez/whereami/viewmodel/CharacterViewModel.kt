package com.ignacioperez.whereami.viewmodel


import android.util.Log
import androidx.core.os.trace
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ignacioperez.whereami.models.CharacterResponse
import com.ignacioperez.whereami.models.Item
import com.ignacioperez.whereami.models.ItemChangeStats
import com.ignacioperez.whereami.models.StatResponse
import com.ignacioperez.whereami.models.StatsModifiedCharacter
import com.ignacioperez.whereami.retrofitInterface.RetrofitServiceFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CharacterViewModel : ViewModel() {
    private val _defaultCharacters = MutableLiveData<List<CharacterResponse>>()
    val defaultCharacters: LiveData<List<CharacterResponse>> = _defaultCharacters

    private var _selectedCharacter = MutableLiveData<CharacterResponse>()
    val selectedCharacter: LiveData<CharacterResponse> = _selectedCharacter

    private var _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private var _responseError = MutableLiveData<Boolean>()
    val responseError: LiveData<Boolean> = _responseError

    private val _itemsCharacter = MutableLiveData<List<Item>>()
    val itemsCharacter: LiveData<List<Item>> = _itemsCharacter

    private val _statsBaseCharacter = MutableLiveData<StatResponse>()
    val statsBaseCharacter: LiveData<StatResponse> = _statsBaseCharacter

    private val _statsModified = MutableLiveData<StatsModifiedCharacter>()
    val statsModified: LiveData<StatsModifiedCharacter> = _statsModified

    fun loadCharacters() {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.postValue(true)
            val service = RetrofitServiceFactory.getRetrofit()
            try {
                val result = service.getAllDefaultCharacters()
                _defaultCharacters.postValue(result)
                _responseError.postValue(false)
            } catch (e: Exception) {
                _responseError.postValue(true)
            }
            _isLoading.postValue(true)
        }
    }

    fun loadCharacter(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.postValue(true)
            val service = RetrofitServiceFactory.getRetrofit()
            try {
                val result = service.getCharacterById(id)
                _selectedCharacter.postValue(result)
                _responseError.postValue(false)
            } catch (e: Exception) {
                _responseError.postValue(true)
            }
            _isLoading.postValue(false)
        }
    }

    fun loadItemsCharacter(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.postValue(true)
            val service = RetrofitServiceFactory.getRetrofit()
            try {
                val respult = service.getItemsByCharacter(id)
                _itemsCharacter.postValue(respult)
                _responseError.postValue(false)
            } catch (e: Exception) {
                _responseError.postValue(true)
            }
            _isLoading.postValue(false)
        }
    }

    fun loadStatsBase(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.postValue(true)
            val service = RetrofitServiceFactory.getRetrofit()
            try {
                val result = service.getStatsBase(id)
                _statsBaseCharacter.postValue(result)
                _responseError.postValue(false)
            } catch (e: Exception) {
                _responseError.postValue(true)
            }
        }
        _isLoading.postValue(false)
    }

    fun loadStatsModified(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.postValue(true)
            val service = RetrofitServiceFactory.getRetrofit()
            try {
                val result = service.getStatsModified(id)
                _statsModified.postValue(result)
                _responseError.postValue(false)
            } catch (e: Exception) {
                _responseError.postValue(true)
            }
            _isLoading.postValue(false)
        }
    }

    fun onCharacterClicked(character: CharacterResponse) {
        _selectedCharacter.value = character
        loadItemsCharacter(character.id)
        loadStatsBase(character.id)
        loadStatsModified(character.id)
    }
}