package com.ignacioperez.whereami.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ignacioperez.whereami.models.User
import com.ignacioperez.whereami.retrofitInterface.RetrofitServiceFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {
    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    private var _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private var _responseError = MutableLiveData<Boolean>()
    val responseError: LiveData<Boolean> = _responseError

    suspend fun getLastUser(): Int {
        _isLoading.postValue(true)
        delay(2000)
        val service = RetrofitServiceFactory.getRetrofit()
        try {
            val user = service.getLastUser()
            _responseError.postValue(false)
            Log.i("UserViewModel", "Server response: $user")
            Log.i("UserViewModel", "User id: ${user.id}")
            return user.id ?: -1
        } catch (e: Exception) {
            _responseError.postValue(true)
            return -1
        } finally {
            _isLoading.postValue(false)
        }
    }


    fun createUser(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.postValue(true)
            delay(2000)
            val service = RetrofitServiceFactory.getRetrofit()
            try {
                val response = service.createUser(user)
                _user.postValue(response)
                _responseError.postValue(false)
            } catch (e: Exception) {
                _responseError.postValue(true)
            }
            _isLoading.postValue(false)
        }
    }
}