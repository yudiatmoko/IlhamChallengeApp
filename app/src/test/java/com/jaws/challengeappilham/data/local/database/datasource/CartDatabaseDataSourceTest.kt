package com.jaws.challengeappilham.data.local.database.datasource

import app.cash.turbine.test
import com.jaws.challengeappilham.data.local.database.dao.CartDao
import com.jaws.challengeappilham.data.local.database.entity.CartEntity
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class CartDatabaseDataSourceTest {

    @MockK
    lateinit var cartDao: CartDao

    private lateinit var cartDatabaseDataSource: CartDatabaseDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        cartDatabaseDataSource = CartDatabaseDataSource(cartDao)
    }

    @Test
    fun getAllCarts() {
        val mockCartEntity1 = mockk<CartEntity>()
        val mockCartEntity2 = mockk<CartEntity>()
        val listMockCartEntities = listOf(mockCartEntity1, mockCartEntity2)
        val flowMock = flow {
            emit(listMockCartEntities)
        }
        coEvery { cartDao.getAllCarts() } returns flowMock
        runTest {
            cartDatabaseDataSource.getAllCarts().test {
                val itemResult = awaitItem()
                assertEquals(listMockCartEntities, itemResult)
                assertEquals(mockCartEntity1, itemResult[0])
                assertEquals(mockCartEntity2, itemResult[1])
                awaitComplete()
            }
        }
    }

    @Test
    fun getCartById() {
        val mockCartEntity = mockk<CartEntity>()
        val flowMock = flow { emit(mockCartEntity) }
        coEvery { cartDao.getCartById(1) } returns flowMock
        runTest {
            cartDatabaseDataSource.getCartById(1).test {
                val itemResult = awaitItem()
                assertEquals(mockCartEntity, itemResult)
                awaitComplete()
            }
        }
    }

    @Test
    fun insertCart() {
        runTest {
            val mockCartEntity = mockk<CartEntity>()
            coEvery { cartDao.insertCart(any()) } returns 1
            val result = cartDatabaseDataSource.insertCart(mockCartEntity)
            coVerify { cartDao.insertCart(any()) }
            assertEquals(result, 1)
        }
    }

    @Test
    fun deleteCart() {
        runTest {
            val mockCartEntity = mockk<CartEntity>()
            coEvery { cartDao.deleteCart(any()) } returns 1
            val result = cartDatabaseDataSource.deleteCart(mockCartEntity)
            coVerify { cartDao.deleteCart(any()) }
            assertEquals(result, 1)
        }
    }

    @Test
    fun updateCart() {
        runTest {
            val mockCartEntity = mockk<CartEntity>()
            coEvery { cartDao.updateCart(any()) } returns 1
            val result = cartDatabaseDataSource.updateCart(mockCartEntity)
            coVerify { cartDao.updateCart(any()) }
            assertEquals(result, 1)
        }
    }

    @Test
    fun deleteAll() {
        runTest {
            coEvery { cartDao.deleteAll() } returns Unit
            val result = cartDatabaseDataSource.deleteAll()
            coVerify { cartDao.deleteAll() }
            assertEquals(result, Unit)
        }
    }
}
