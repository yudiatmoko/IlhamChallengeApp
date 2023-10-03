package com.jaws.challengeappilham.data.repository

import com.jaws.challengeappilham.data.local.database.datasource.CartDataSource
import com.jaws.challengeappilham.data.local.database.mapper.toCartMenuList
import com.jaws.challengeappilham.model.CartMenu
import com.jaws.challengeappilham.utils.ResultWrapper
import com.jaws.challengeappilham.utils.proceed
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

interface CartRepository {
    fun getCartList(): Flow<ResultWrapper<List<CartMenu>>>
}

class CartRepositoryImpl(
    private val dataSource: CartDataSource
) : CartRepository {
    override fun getCartList(): Flow<ResultWrapper<List<CartMenu>>> {
        return dataSource.getAllCarts().map { proceed { it.toCartMenuList() } }.onStart {
            emit(ResultWrapper.Loading())
            delay(2000)
        }
    }
}