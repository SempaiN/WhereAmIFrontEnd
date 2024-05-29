package com.ignacioperez.whereami.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ignacioperez.whereami.models.ListPills
import com.ignacioperez.whereami.models.ObjectChangeStatsList
import com.ignacioperez.whereami.models.Pill
import com.ignacioperez.whereami.retrofitInterface.RetrofitServiceFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PillViewModel : ViewModel() {
    private val _selectedPill = MutableLiveData<Pill>()
    val selectedPill: LiveData<Pill> = _selectedPill

    private val _statsChangedByPill = MutableLiveData<ObjectChangeStatsList>()
    val statsChangedByPill: LiveData<ObjectChangeStatsList> = _statsChangedByPill

    private val _allPills = MutableLiveData<ListPills>()
    val allPills: LiveData<ListPills> = _allPills

    private var _responseError = MutableLiveData<Boolean>()
    val responseError: LiveData<Boolean> = _responseError

    fun loadStats(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val service = RetrofitServiceFactory.getRetrofit()
            try {
                val result = service.getStatsModifiedByPickup(id)
                _statsChangedByPill.postValue(result)
                _responseError.postValue(false)
            } catch (e: Exception) {
                _responseError.postValue(true)
            }
        }
    }

    fun onPillClicked(pill: Pill) {
        _selectedPill.value = pill
        loadStats(pill.id)
    }

    fun getAllPills() {
        viewModelScope.launch(Dispatchers.IO) {
            val service = RetrofitServiceFactory.getRetrofit()
            try {
                val result = service.getAllPills()
                _allPills.postValue(result)
                _responseError.postValue(false)
            } catch (e: Exception) {
                _responseError.postValue(true)
            }
        }
    }
}