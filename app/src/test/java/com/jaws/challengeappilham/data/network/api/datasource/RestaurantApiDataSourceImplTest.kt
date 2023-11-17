package com.jaws.challengeappilham.data.network.api.datasource

import com.jaws.challengeappilham.data.network.api.model.category.CategoriesResponse
import com.jaws.challengeappilham.data.network.api.model.menu.MenusResponse
import com.jaws.challengeappilham.data.network.api.model.order.OrderRequest
import com.jaws.challengeappilham.data.network.api.model.order.OrdersResponse
import com.jaws.challengeappilham.data.network.api.service.RestaurantService
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class RestaurantApiDataSourceImplTest {

    @MockK
    lateinit var service: RestaurantService

    private lateinit var dataSource: RestaurantApiDataSourceImpl

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        dataSource = RestaurantApiDataSourceImpl(service)
    }

    @Test
    fun getMenus() {
        runTest {
            val mockResponse = mockk<MenusResponse>(relaxed = true)
            coEvery { service.getMenus(any()) } returns mockResponse
            val response = dataSource.getMenus("makanan")
            coVerify { service.getMenus(any()) }
            assertEquals(response, mockResponse)
        }
    }

    @Test
    fun getCategories() {
        runTest {
            val mockResponse = mockk<CategoriesResponse>(relaxed = true)
            coEvery { service.getCategories() } returns mockResponse
            val response = dataSource.getCategories()
            coVerify { service.getCategories() }
            assertEquals(response, mockResponse)
        }
    }

    @Test
    fun createOrder() {
        runTest {
            val mockResponse = mockk<OrdersResponse>(relaxed = true)
            val mockRequest = mockk<OrderRequest>(relaxed = true)
            coEvery { service.createOrder(any()) } returns mockResponse
            val response = dataSource.createOrder(mockRequest)
            coVerify { service.createOrder(any()) }
            assertEquals(response, mockResponse)
        }
    }
}
