package com.ignacioperez.whereami.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ignacioperez.whereami.models.CharacterResponse
import com.ignacioperez.whereami.retrofitInterface.RetrofitServiceFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CharacterViewModel : ViewModel() {
    private val _defaultCharacters = MutableLiveData<List<CharacterResponse>>()
    val defaultCharacters: LiveData<List<CharacterResponse>> = _defaultCharacters

    private val _customCharacters = MutableLiveData<List<CharacterResponse>>()
    val customCharacters: LiveData<List<CharacterResponse>> = _customCharacters

    private var _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private var _responseError = MutableLiveData<Boolean>()
    val responseError: LiveData<Boolean> = _responseError

    fun loadCharacters() {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.postValue(true)
            val service = RetrofitServiceFactory.getRetrofit()
            Log.i("Characters", service.getAllDefaultCharacters().toString())
            try {
                val result = service.getAllDefaultCharacters()
                Log.i("Characters", result.toString())
                _defaultCharacters.postValue(result)
                _responseError.postValue(false)
            } catch (e: Exception) {
                _responseError.postValue(true)
            }
            _isLoading.postValue(true)
        }
    }
}