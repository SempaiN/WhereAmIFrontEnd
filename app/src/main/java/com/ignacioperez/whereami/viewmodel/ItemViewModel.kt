package com.ignacioperez.whereami.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ignacioperez.whereami.models.Item
import com.ignacioperez.whereami.models.ObjectChangeStatsList
import com.ignacioperez.whereami.retrofitInterface.RetrofitServiceFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ItemViewModel : ViewModel() {
    private val _selectedItem = MutableLiveData<Item>()
    val selectedItem: LiveData<Item> = _selectedItem

    private val _statsChangedByItem = MutableLiveData<ObjectChangeStatsList>()
    val statsChangedByItem: LiveData<ObjectChangeStatsList> = _statsChangedByItem

    private val _allItems = MutableLiveData<List<Item>>()
    val allItems: LiveData<List<Item>> = _allItems

    private var _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private var _responseError = MutableLiveData<Boolean>()
    val responseError: LiveData<Boolean> = _responseError

    fun onItemClicked(item: Item) {
        _selectedItem.value = item
        loadStats(item.id)
    }

    fun clearStats() {
        _statsChangedByItem.postValue(ObjectChangeStatsList())
    }

    fun loadStats(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.postValue(true)
            val service = RetrofitServiceFactory.getRetrofit()
            try {
                val result = service.getStatsChanges(id)
                _statsChangedByItem.postValue(result)
                _responseError.postValue(false)
            } catch (e: Exception) {
                _responseError.postValue(true)
            }
        }
    }

    fun loadItem(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.postValue(true)
            delay(2000)
            val service = RetrofitServiceFactory.getRetrofit()
            try {
                val result = service.getItemById(id)
                _selectedItem.postValue(result.result)
                _responseError.postValue(false)
            } catch (e: Exception) {
                _responseError.postValue(true)
            }
            _isLoading.postValue(false)
        }
    }

    fun getAllItems() {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.postValue(true)
            val service = RetrofitServiceFactory.getRetrofit()
            try {
                val result = service.getAllItems()
                Log.i("--", result.toString())
                _allItems.postValue(result)
                _responseError.postValue(false)
            } catch (e: Exception) {
                Log.i("--", e.message.toString())
                _responseError.postValue(true)
            }
            _isLoading.postValue(false)
        }
    }


}