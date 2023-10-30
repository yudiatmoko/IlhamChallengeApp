package com.jaws.challengeappilham.presentation.menudetail

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.jaws.challengeappilham.data.repository.CartRepository
import com.jaws.challengeappilham.model.Menu
import com.jaws.challengeappilham.utils.ResultWrapper
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch

class MenuDetailViewModel @AssistedInject constructor(
    private val cartRepository: CartRepository,
    @Assisted
    private val extras: Bundle?
) : ViewModel() {

    val menu = extras?.getParcelable<Menu>(MenuDetailActivity.EXTRA_PRODUCT)

    val priceLiveData = MutableLiveData<Double>().apply {
        postValue(0.0)
    }
    val menuCountLiveData = MutableLiveData<Int>().apply {
        postValue(0)
    }
    private val _addToCartResult = MutableLiveData<ResultWrapper<Boolean>>()
    val addToCartResult: LiveData<ResultWrapper<Boolean>>
        get() = _addToCartResult

    fun add() {
        val count = (menuCountLiveData.value ?: 0) + 1
        menuCountLiveData.postValue(count)
        priceLiveData.postValue((menu?.price?.times(count) ?: 0.0).toDouble())
    }

    fun minus() {
        if ((menuCountLiveData.value ?: 0) > 0) {
            val count = (menuCountLiveData.value ?: 0) - 1
            menuCountLiveData.postValue(count)
            priceLiveData.postValue((menu?.price?.times(count) ?: 0.0).toDouble())
        }
    }

    fun addToCart() {
        viewModelScope.launch {
            val qty =
                if ((menuCountLiveData.value ?: 0) <= 0) 1 else menuCountLiveData.value ?: 0
            menu?.let {
                cartRepository.createCart(it, qty).collect { result ->
                    _addToCartResult.postValue(result)
                }
            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(extras: Bundle?): MenuDetailViewModel
    }
    companion object {
        fun provideMenuDetailViewModelFactory(factory: Factory, extras: Bundle?): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(
                    modelClass: Class<T>
                ): T {
                    return factory.create(extras) as T
                }
            }
        }
    }
}
