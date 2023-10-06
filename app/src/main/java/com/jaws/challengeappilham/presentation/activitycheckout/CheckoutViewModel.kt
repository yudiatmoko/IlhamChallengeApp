package com.jaws.challengeappilham.presentation.activitycheckout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.jaws.challengeappilham.data.repository.CartRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jetbrains.annotations.Async

class CheckoutViewModel(private val cartRepository: CartRepository) : ViewModel() {
    val cartList = cartRepository.getCartList().asLiveData(Dispatchers.IO)

    fun deleteAll(){
        viewModelScope.launch {
            cartRepository.deleteAll()
        }
    }
}