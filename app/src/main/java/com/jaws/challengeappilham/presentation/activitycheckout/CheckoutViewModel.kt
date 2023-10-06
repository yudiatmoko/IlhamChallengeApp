package com.jaws.challengeappilham.presentation.activitycheckout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.jaws.challengeappilham.data.repository.CartRepository
import kotlinx.coroutines.Dispatchers

class CheckoutViewModel(private val cartRepository: CartRepository) : ViewModel() {
    val cartList = cartRepository.getCartList().asLiveData(Dispatchers.IO)
}