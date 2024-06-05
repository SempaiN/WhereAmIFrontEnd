package com.ignacioperez.whereami.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ignacioperez.whereami.models.ListPills
import com.ignacioperez.whereami.models.ObjectChangeStatsList
import com.ignacioperez.whereami.models.Pill
import com.ignacioperez.whereami.models.User
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

    private val _showPillDetails = MutableLiveData<Boolean>()
    val showPillDetails: LiveData<Boolean> = _showPillDetails

    private val _isSelectedPillFavorite = MutableLiveData<Boolean>()
    val isSelectedPillFavorite: LiveData<Boolean> = _isSelectedPillFavorite
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

    fun checkPillFavorite(pill: Pill, user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            val service = RetrofitServiceFactory.getRetrofit()
            try {
                val result = service.isPickupFavorite(pill.id, user.id)
                _isSelectedPillFavorite.postValue(result)
            } catch (e: Exception) {
                Log.i("Error", e.toString())
                _isSelectedPillFavorite.postValue(false)
            }
        }
    }

    fun insertPillFavorite(pill: Pill, user: User, userViewModel: UserViewModel) {
        viewModelScope.launch(Dispatchers.IO) {
            val service = RetrofitServiceFactory.getRetrofit()
            try {
                service.insertPickupFavorite(pill.id, user.id)
                checkPillFavorite(pill, user)
                userViewModel.getFavoritePills(user)
            } catch (e: Exception) {
                Log.i("--", e.message.toString())
            }
        }
    }

    fun deletePillFavorite(pill: Pill, user: User, userViewModel: UserViewModel) {
        viewModelScope.launch(Dispatchers.IO) {
            val service = RetrofitServiceFactory.getRetrofit()
            try {
                service.deletePickupFavorite(user.id, pill.id)
                checkPillFavorite(pill, user)
                userViewModel.getFavoritePills(user)
            } catch (e: Exception) {
                Log.i("--", e.message.toString())
            }
        }
    }

    fun clearSelectedPill() {
        _selectedPill.value = Pill()
    }

    fun showPillAlertDialog() {
        _showPillDetails.value = true
    }

    fun hidePillAlertDialog() {
        _showPillDetails.value = false
    }

    fun onPillClicked(pill: Pill) {
        _selectedPill.value = pill
        loadStats(pill.id)
        showPillAlertDialog()
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