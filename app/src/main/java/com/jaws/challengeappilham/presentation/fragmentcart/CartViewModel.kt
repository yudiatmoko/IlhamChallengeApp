package com.jaws.challengeappilham.presentation.fragmentcart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.jaws.challengeappilham.data.repository.CartRepository
import kotlinx.coroutines.Dispatchers

class CartViewModel(private val repo : CartRepository) : ViewModel() {

    val cartList = repo.getCartList().asLiveData(Dispatchers.IO)
}