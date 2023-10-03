package com.jaws.challengeappilham.data.local.database.datasource

import com.jaws.challengeappilham.data.local.database.dao.CartDao
import com.jaws.challengeappilham.data.local.database.entity.CartEntity
import com.jaws.challengeappilham.data.local.database.relation.CartMenuRelation
import kotlinx.coroutines.flow.Flow

interface CartDataSource {
    fun getAllCarts(): Flow<List<CartMenuRelation>>
    fun getCartById(cartId: Int): Flow<CartMenuRelation>
    suspend fun insertCart(cart: CartEntity)
    suspend fun deleteProduct(cart: CartEntity): Int
    suspend fun updateProduct(cart: CartEntity): Int
}

class CartDatabaseDataSource(private val cartDao: CartDao) : CartDataSource {
    override fun getAllCarts(): Flow<List<CartMenuRelation>> {
        return cartDao.getAllCarts()
    }

    override fun getCartById(cartId: Int): Flow<CartMenuRelation> {
        return cartDao.getCartById(cartId)
    }

    override suspend fun insertCart(cart: CartEntity) {
        return cartDao.insertCart(cart)
    }

    override suspend fun deleteProduct(cart: CartEntity): Int {
        return cartDao.deleteProduct(cart)
    }

    override suspend fun updateProduct(cart: CartEntity): Int {
        return cartDao.updateProduct(cart)
    }
}