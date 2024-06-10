package com.ignacioperez.whereami.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ignacioperez.whereami.models.Item
import com.ignacioperez.whereami.models.ObjectChangeStatsList
import com.ignacioperez.whereami.models.User
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

    private val _itemsActives = MutableLiveData<List<Item>>()
    val itemsActives: LiveData<List<Item>> = _itemsActives

    private val _unlockableItems = MutableLiveData<List<Item>>()
    val unlockalbeItems: LiveData<List<Item>> = _unlockableItems


    private val _isSelectedItemFavorite = MutableLiveData<Boolean>()
    val isSelectedItemFavorite: LiveData<Boolean> = _isSelectedItemFavorite

    fun checkFavoriteItem(item: Item, user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            val service = RetrofitServiceFactory.getRetrofit()
            try {
                val result = service.isItemFavorite(item.id, user.id)
                _isSelectedItemFavorite.postValue(result)
            } catch (e: Exception) {
                _isSelectedItemFavorite.postValue(false)
            }
        }
    }

    fun onItemClicked(item: Item) {
        _selectedItem.value = item
        loadStats(item.id)
    }

    fun clearStats() {
        _statsChangedByItem.postValue(ObjectChangeStatsList())
    }

    fun loadStats(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val service = RetrofitServiceFactory.getRetrofit()
            try {
                val result = service.getStatsChanges(id)
                _statsChangedByItem.postValue(result)
            } catch (e: Exception) {
                _statsChangedByItem.postValue(ObjectChangeStatsList())
            }
        }
    }

    suspend fun loadStatsResponseObject(id: Int): ObjectChangeStatsList {
        val service = RetrofitServiceFactory.getRetrofit()
        return try {
            val result = service.getStatsChanges(id)
            _statsChangedByItem.postValue(result)
            result
        } catch (e: Exception) {
            ObjectChangeStatsList()
        }
    }

    fun insertItemFavorite(item: Item, user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            val service = RetrofitServiceFactory.getRetrofit()
            try {
                service.insertItemFavorite(item.id, user.id)
                checkFavoriteItem(item, user)
            } catch (e: Exception) {
            }
        }
    }

    fun deleteItemFavorite(item: Item, user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            val service = RetrofitServiceFactory.getRetrofit()
            try {
                service.deleteItemFavorite(user.id, item.id)
                checkFavoriteItem(item, user)
            } catch (e: Exception) {
            }
        }
    }

    fun getAllItems() {
        viewModelScope.launch(Dispatchers.IO) {
            val service = RetrofitServiceFactory.getRetrofit()
            try {
                val result = service.getAllItems()
                _allItems.postValue(result)
            } catch (e: Exception) {
                _allItems.postValue(emptyList())
            }

        }
    }

    fun getActiveItems() {
        viewModelScope.launch(Dispatchers.IO) {
            val service = RetrofitServiceFactory.getRetrofit()
            try {
                val result = service.getActiveItems()
                _itemsActives.postValue(result)
            } catch (e: Exception) {
                _itemsActives.postValue(emptyList())
            }

        }
    }

    fun getUnlockableItems() {
        viewModelScope.launch(Dispatchers.IO) {
            val service = RetrofitServiceFactory.getRetrofit()
            try {
                val result = service.getUnlockableItems()
                _unlockableItems.postValue(result)
            } catch (e: Exception) {
                _unlockableItems.postValue(emptyList())
            }

        }
    }


}