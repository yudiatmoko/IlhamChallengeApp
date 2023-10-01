package com.jaws.challengeappilham.presentation.activitydetail

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jaws.challengeappilham.model.Menu

class MenuDetailViewModel(private val extras: Bundle?) : ViewModel(){

    val menu = extras?.getParcelable<Menu>(ActivityMenuDetail.EXTRA_PRODUCT)

    val priceLiveData = MutableLiveData<Double>().apply {
        postValue(0.0)
    }
    val menuCountLiveData = MutableLiveData<Int>().apply {
        postValue(0)
    }

    fun add() {
        val count = (menuCountLiveData.value ?: 0) + 1
        menuCountLiveData.postValue(count)
        priceLiveData.postValue(menu?.menuPrice?.times(count) ?: 0.0)
    }

    fun minus() {
        if((menuCountLiveData.value ?: 0) > 0){
            val count = (menuCountLiveData.value ?: 0) -1
            menuCountLiveData.postValue(count)
            priceLiveData.postValue(menu?.menuPrice?.times(count) ?: 0.0)
        }
    }

}