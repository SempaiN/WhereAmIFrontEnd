package com.ignacioperez.whereami.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ignacioperez.whereami.models.Item
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



    private val _pillsNeutral = MutableLiveData<ListPills>()
    val pillsNeutral: LiveData<ListPills> = _pillsNeutral

    private val _pillsNegative = MutableLiveData<ListPills>()
    val pillsNegative: LiveData<ListPills> = _pillsNegative

    private val _pillsPositive = MutableLiveData<ListPills>()
    val pillsPositive: LiveData<ListPills> = _pillsPositive

    private val _pillsUnlockable = MutableLiveData<ListPills>()
    val pillsUnlockable: LiveData<ListPills> = _pillsUnlockable

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

    fun getPillsNeutral() {
        viewModelScope.launch(Dispatchers.IO) {
            val service = RetrofitServiceFactory.getRetrofit()
            try {
                val result = service.getNeutralPills()
                _pillsNeutral.postValue(result)
                _responseError.postValue(false)
            } catch (e: Exception) {
                _responseError.postValue(true)
            }
        }
    }

    fun getPillsNegative() {
        viewModelScope.launch(Dispatchers.IO) {
            val service = RetrofitServiceFactory.getRetrofit()
            try {
                val result = service.getNegativePills()
                _pillsNegative.postValue(result)
                _responseError.postValue(false)
            } catch (e: Exception) {
                _responseError.postValue(true)
            }
        }
    }

    fun getPillsPositive() {
        viewModelScope.launch(Dispatchers.IO) {
            val service = RetrofitServiceFactory.getRetrofit()
            try {
                val result = service.getPositivePills()
                _pillsPositive.postValue(result)
                _responseError.postValue(false)
            } catch (e: Exception) {
                _responseError.postValue(true)
            }
        }
    }

    fun getUnlockablePills() {
        viewModelScope.launch(Dispatchers.IO) {
            val service = RetrofitServiceFactory.getRetrofit()
            try {
                val result = service.getUnlockablePills()
                _pillsUnlockable.postValue(result)
                _responseError.postValue(false)
            } catch (e: Exception) {
                _responseError.postValue(true)
            }
        }
    }
}