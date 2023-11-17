package com.jaws.challengeappilham.presentation.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.jaws.challengeappilham.data.repository.CartRepository
import com.jaws.challengeappilham.model.Cart
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(private val repo: CartRepository) : ViewModel() {

    val cartList = repo.getCartList().asLiveData(Dispatchers.IO)

    fun decreaseCart(item: Cart) {
        viewModelScope.launch(Dispatchers.IO) { repo.decreaseCart(item).collect() }
    }

    fun increaseCart(item: Cart) {
        viewModelScope.launch(Dispatchers.IO) { repo.increaseCart(item).collect() }
    }

    fun deleteCart(item: Cart) {
        viewModelScope.launch(Dispatchers.IO) { repo.deleteCart(item).collect() }
    }

    fun updateNotes(item: Cart) {
        viewModelScope.launch(Dispatchers.IO) { repo.setCartNotes(item).collect() }
    }
}
