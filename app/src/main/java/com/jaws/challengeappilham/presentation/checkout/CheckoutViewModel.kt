package com.jaws.challengeappilham.presentation.checkout

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.jaws.challengeappilham.data.repository.CartRepository
import com.jaws.challengeappilham.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CheckoutViewModel(
    private val cartRepository: CartRepository,
) : ViewModel() {

    val cartList = cartRepository.getCartList().asLiveData(Dispatchers.IO)

    private val _orderResult = MutableLiveData<ResultWrapper<Boolean>>()

    val orderResult: LiveData<ResultWrapper<Boolean>>
        get() = _orderResult

    fun deleteAll() {
        viewModelScope.launch(Dispatchers.IO) {
            cartRepository.deleteAll()
        }
    }

    fun createOrder() {
        viewModelScope.launch(Dispatchers.IO) {
            val carts = cartList.value?.payload?.first ?: return@launch
            cartRepository.order(carts).collect {
                _orderResult.postValue(it)
            }
        }
    }
}
