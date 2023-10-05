package com.jaws.challengeappilham.data.local.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.jaws.challengeappilham.data.local.database.entity.CartEntity
import com.jaws.challengeappilham.data.local.database.relation.CartMenuRelation
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao{
    @Query("SELECT * FROM CARTS")
    fun getAllCarts(): Flow<List<CartMenuRelation>>

    @Query("SELECT * FROM CARTS WHERE id == :cartId")
    fun getCartById(cartId: Int): Flow<CartMenuRelation>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCart(cart: CartEntity) : Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCarts(product: List<CartEntity>)

    @Delete
    suspend fun deleteCart(cart: CartEntity): Int

    @Update
    suspend fun updateCart(cart: CartEntity): Int
}