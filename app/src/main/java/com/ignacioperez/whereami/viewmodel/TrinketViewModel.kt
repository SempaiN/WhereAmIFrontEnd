package com.ignacioperez.whereami.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ignacioperez.whereami.models.ListTrinkets
import com.ignacioperez.whereami.models.ObjectChangeStatsList
import com.ignacioperez.whereami.models.Trinket
import com.ignacioperez.whereami.retrofitInterface.RetrofitServiceFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TrinketViewModel : ViewModel() {

    private val _selectedTrinket = MutableLiveData<Trinket>()
    val selectedTrinket: LiveData<Trinket> = _selectedTrinket

    private val _allTrinkets = MutableLiveData<ListTrinkets>()
    val allTrinkets: LiveData<ListTrinkets> = _allTrinkets

    private var _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private var _responseError = MutableLiveData<Boolean>()
    val responseError: LiveData<Boolean> = _responseError

    private val _statsChangedByTrinket = MutableLiveData<ObjectChangeStatsList>()
    val statsChangedByTrinket: LiveData<ObjectChangeStatsList> = _statsChangedByTrinket
    fun onTrinketClicked(trinket: Trinket) {
        _selectedTrinket.value = trinket
        loadStats(trinket.id)
    }

    fun loadStats(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.postValue(true)
            val service = RetrofitServiceFactory.getRetrofit()
            try {
                val result = service.getStatsChangedByTrinket(id)
                _statsChangedByTrinket.postValue(result)
                _responseError.postValue(false)
            } catch (e: Exception) {
                _responseError.postValue(true)
            }
            _isLoading.postValue(false)
        }
    }

    fun getAllTrinkets() {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.postValue(true)
            val service = RetrofitServiceFactory.getRetrofit()
            try {
                val result = service.getAllTrinkets()
                _allTrinkets.postValue(result)
                _responseError.postValue(false)
            } catch (e: Exception) {
                _responseError.postValue(true)
            }
            _isLoading.postValue(false)
        }
    }
}