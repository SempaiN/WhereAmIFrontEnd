package com.ignacioperez.whereami.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ignacioperez.whereami.models.Character
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

    private val _charactersCustom = MutableLiveData<List<Character>>()
    val charactersCustom: LiveData<List<Character>> = _charactersCustom

    fun getCharactersCustom() {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.postValue(true)
            delay(2000)
            val service = RetrofitServiceFactory.getRetrofit()
            try {
                val result = service.getCharactersByUser(_user.value!!.id)
                _charactersCustom.postValue(result.characters)
                _responseError.postValue(false)
            } catch (e: Exception) {
                _responseError.postValue(true)
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
                setCurrentUser(User(result.result.id, result.result.name, email))
                _responseError.postValue(false)
            } catch (e: Exception) {
                _responseError.postValue(true)
            }
            _isLoading.postValue(false)
        }
    }

    fun setCurrentUser(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            _user.value = user
        }
    }
}