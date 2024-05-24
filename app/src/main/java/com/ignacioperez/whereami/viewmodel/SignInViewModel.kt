package com.ignacioperez.whereami.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ignacioperez.whereami.models.CharacterResponse
import com.ignacioperez.whereami.models.User
import com.ignacioperez.whereami.retrofitInterface.RetrofitServiceFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SignInViewModel : ViewModel() {
    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    private var _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private var _responseError = MutableLiveData<Boolean>()
    val responseError: LiveData<Boolean> = _responseError

    private val _charactersCustom = MutableLiveData<List<CharacterResponse>>()
    val charactersCustom: LiveData<List<CharacterResponse>> = _charactersCustom

    fun getCharactersCustom(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.postValue(true)
            delay(2000)
            val service = RetrofitServiceFactory.getRetrofit()
            try {
                val result = service.getCharactersByUser(user.id)
                Log.i("characters", result.toString())
                _charactersCustom.postValue(result)
                _responseError.postValue(false)
            } catch (e: Exception) {
                _responseError.postValue(true)
                Log.i("Error message", e.message.toString())

                _charactersCustom.postValue(emptyList())
            }
            _isLoading.postValue(false)
        }
    }

    fun getUserFromDB(email: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.postValue(true)
            delay(2000)
            val service = RetrofitServiceFactory.getRetrofit()
            try {
                val result = service.getUserByEmail(email)
                _user.postValue(result)
                Log.i(
                    "User Result",
                    result.toString()
                )
                _responseError.postValue(false)
            } catch (e: Exception) {
                Log.i("Error message", e.message.toString())
                Log.i("Error message", _user.value.toString())
                _responseError.postValue(true)
            }
            _isLoading.postValue(false)
        }
    }

    fun getCurrentUser(): User {
        return _user.value!!
    }

    fun setCurrentUser(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            _user.value = user
        }
    }
}