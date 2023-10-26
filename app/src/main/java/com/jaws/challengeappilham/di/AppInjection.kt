package com.jaws.challengeappilham.di

import android.os.Bundle
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.jaws.challengeappilham.App
import com.jaws.challengeappilham.data.local.database.AppDatabase
import com.jaws.challengeappilham.data.local.database.datasource.CartDataSource
import com.jaws.challengeappilham.data.local.database.datasource.CartDatabaseDataSource
import com.jaws.challengeappilham.data.network.api.datasource.RestaurantApiDataSourceImpl
import com.jaws.challengeappilham.data.network.api.service.RestaurantService
import com.jaws.challengeappilham.data.repository.CartRepository
import com.jaws.challengeappilham.data.repository.CartRepositoryImpl
import com.jaws.challengeappilham.presentation.menudetail.MenuDetailViewModel
import com.jaws.challengeappilham.utils.GenericViewModelFactory

object AppInjection {
    val database = AppDatabase.getInstance(App.appContext())
    val cartDao = database.cartDao()
    val chucker = ChuckerInterceptor(App.appContext())
    val service = RestaurantService.invoke(chucker)
    val orderDataSource = RestaurantApiDataSourceImpl(service)
    val cartDataSource: CartDataSource = CartDatabaseDataSource(cartDao)
    val cartRepo: CartRepository = CartRepositoryImpl(cartDataSource, orderDataSource)

    fun getDetailMenuHomeViewModel(extras: Bundle?) = GenericViewModelFactory.create(
        MenuDetailViewModel(
            extras,
            cartRepo
        )
    )
}