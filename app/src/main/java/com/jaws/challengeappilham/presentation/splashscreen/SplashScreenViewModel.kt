package com.jaws.challengeappilham.presentation.splashscreen

import androidx.lifecycle.ViewModel
import com.jaws.challengeappilham.data.repository.UserRepository

class SplashScreenViewModel(private val repo: UserRepository) : ViewModel() {

    fun isUserLoggedIn() = repo.isLoggedIn()
}
