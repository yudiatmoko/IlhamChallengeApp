package com.jaws.challengeappilham.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jaws.challengeappilham.data.repository.UserRepository
import com.jaws.challengeappilham.utils.ResultWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repo: UserRepository) : ViewModel() {

    private val _loginResult = MutableLiveData<ResultWrapper<Boolean>>()

    val loginResult: LiveData<ResultWrapper<Boolean>>
        get() = _loginResult

    fun doLogin(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.doLogin(email, password).collect {
                _loginResult.postValue(it)
            }
        }
    }
}
