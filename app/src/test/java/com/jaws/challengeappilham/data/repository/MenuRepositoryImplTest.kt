package com.jaws.challengeappilham.data.repository

import app.cash.turbine.test
import com.jaws.challengeappilham.data.network.api.datasource.RestaurantApiDataSource
import com.jaws.challengeappilham.data.network.api.model.category.CategoriesResponse
import com.jaws.challengeappilham.data.network.api.model.category.CategoryItemResponse
import com.jaws.challengeappilham.data.network.api.model.menu.MenuItemResponse
import com.jaws.challengeappilham.data.network.api.model.menu.MenusResponse
import com.jaws.challengeappilham.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class MenuRepositoryImplTest {

    @MockK
    lateinit var remoteDataSource: RestaurantApiDataSource

    private lateinit var menuRepository: MenuRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        menuRepository = MenuRepositoryImpl(remoteDataSource)
    }

    @Test
    fun `get categories, with result loading`() {
        val mockCategoryResponse = mockk<CategoriesResponse>()
        runTest {
            coEvery { remoteDataSource.getCategories() } returns mockCategoryResponse
            menuRepository.getCategories().map {
                delay(100)
                it
            }.test {
                delay(120)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Loading)
                coVerify { remoteDataSource.getCategories() }
            }
        }
    }

    @Test
    fun `get categories, with result success`() {
        val fakeCategoryItemResponse = CategoryItemResponse(
            id = 123,
            imageUrl = "url",
            name = "name",
            slug = "slug"
        )
        val fakeCategoriesResponse = CategoriesResponse(
            code = 200,
            status = true,
            message = "Success",
            data = listOf(fakeCategoryItemResponse)
        )

        runTest {
            coEvery { remoteDataSource.getCategories() } returns fakeCategoriesResponse
            menuRepository.getCategories().map {
                delay(100)
                it
            }.test {
                delay(220)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                assertEquals(data.payload?.size, 1)
                assertEquals(data.payload?.get(0)?.id, 123)
                coVerify { remoteDataSource.getCategories() }
            }
        }
    }

    @Test
    fun `get categories, with result success but empty`() {
        val fakeCategoriesResponse = CategoriesResponse(
            code = 200,
            status = true,
            message = "Success but Empty",
            data = emptyList()
        )
        runTest {
            coEvery { remoteDataSource.getCategories() } returns fakeCategoriesResponse
            menuRepository.getCategories().map {
                delay(100)
                it
            }.test {
                delay(220)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Empty)
                coVerify { remoteDataSource.getCategories() }
            }
        }
    }

    @Test
    fun `get categories, with result error`() {
        runTest {
            coEvery { remoteDataSource.getCategories() } throws IllegalStateException("Mock Error")
            menuRepository.getCategories().map {
                delay(100)
                it
            }.test {
                delay(220)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Error)
                coVerify { remoteDataSource.getCategories() }
            }
        }
    }

    @Test
    fun `get menus, with result loading`() {
        val mockMenuResponse = mockk<MenusResponse>()
        runTest {
            coEvery { remoteDataSource.getMenus(any()) } returns mockMenuResponse
            menuRepository.getMenus("makanan").map {
                delay(100)
                it
            }.test {
                delay(120)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Loading)
                coVerify { remoteDataSource.getMenus(any()) }
            }
        }
    }

    @Test
    fun `get menus, with result success`() {
        val fakeMenuItemResponse = MenuItemResponse(
            id = 123,
            imageUrl = "url",
            name = "name",
            detail = "detail",
            price = 20.0,
            formattedPrice = "Rp. 20.0",
            restaurantAddress = "address"
        )
        val fakeMenusResponse = MenusResponse(
            code = 200,
            status = true,
            message = "Success",
            data = listOf(fakeMenuItemResponse)
        )
        runTest {
            coEvery { remoteDataSource.getMenus(any()) } returns fakeMenusResponse
            menuRepository.getMenus("makanan").map {
                delay(100)
                it
            }.test {
                delay(220)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                assertEquals(data.payload?.size, 1)
                assertEquals(data.payload?.get(0)?.id, 123)
                coVerify { remoteDataSource.getMenus(any()) }
            }
        }
    }

    @Test
    fun `get menus, with result success but empty`() {
        val fakeMenusResponse = MenusResponse(
            code = 200,
            status = true,
            message = "Success but Empty",
            data = emptyList()
        )
        runTest {
            coEvery { remoteDataSource.getMenus(any()) } returns fakeMenusResponse
            menuRepository.getMenus("makanan").map {
                delay(100)
                it
            }.test {
                delay(220)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Empty)
                coVerify { remoteDataSource.getMenus(any()) }
            }
        }
    }

    @Test
    fun `get menus, with result error`() {
        runTest {
            coEvery { remoteDataSource.getMenus(any()) } throws IllegalStateException("Mock Error")
            menuRepository.getMenus("makanan").map {
                delay(100)
                it
            }.test {
                delay(220)
                val data = expectMostRecentItem()
                println(data)
                assertTrue(data is ResultWrapper.Error)
                coVerify { remoteDataSource.getMenus(any()) }
            }
        }
    }
}
