package com.jaws.challengeappilham.presentation.splashscreen

import androidx.lifecycle.ViewModel
import com.jaws.challengeappilham.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(private val repo: UserRepository) : ViewModel() {

    fun isUserLoggedIn() = repo.isLoggedIn()
}
