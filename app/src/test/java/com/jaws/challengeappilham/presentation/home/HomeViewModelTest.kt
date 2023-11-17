package com.jaws.challengeappilham.presentation.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jaws.challengeappilham.data.repository.MenuRepository
import com.jaws.challengeappilham.data.repository.UserRepository
import com.jaws.challengeappilham.model.Menu
import com.jaws.challengeappilham.model.User
import com.jaws.challengeappilham.tools.MainCoroutineRule
import com.jaws.challengeappilham.tools.getOrAwaitValue
import com.jaws.challengeappilham.utils.AssetWrapper
import com.jaws.challengeappilham.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.spyk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class HomeViewModelTest {

    @MockK
    private lateinit var menuRepo: MenuRepository

    @MockK
    private lateinit var userRepo: UserRepository

    @MockK
    private lateinit var assetWrapper: AssetWrapper

    @get:Rule
    val testRule: TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule: TestRule = MainCoroutineRule(
        UnconfinedTestDispatcher()
    )

    private lateinit var viewModel: HomeViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = spyk(
            HomeViewModel(menuRepo, userRepo, assetWrapper),
            recordPrivateCalls = true
        )
        every { assetWrapper.getString(any()) } returns "all"
        coEvery { menuRepo.getCategories() } returns flow {
            emit(
                ResultWrapper.Success(
                    listOf(
                        mockk(relaxed = true),
                        mockk(relaxed = true),
                        mockk(relaxed = true),
                        mockk(relaxed = true)
                    )
                )
            )
        }
        coEvery { menuRepo.getMenus(any()) } returns flow {
            emit(
                ResultWrapper.Success(
                    listOf(
                        mockk(relaxed = true),
                        mockk(relaxed = true),
                        mockk(relaxed = true),
                        mockk(relaxed = true)
                    )
                )
            )
        }
    }

//    @Test
//    fun `test category live data`() {
//        runTest {
//            viewModel.getCategories()
//            coVerify { menuRepo.getCategories() }
//            val result = viewModel.categories.getOrAwaitValue(3)
//            assert(result is ResultWrapper.Success)
//        }
//    }

    @Test
    fun `test menu live data`() {
        runTest {
            viewModel.getMenus("all")
            coVerify { menuRepo.getMenus(any()) }
            val result = viewModel.menus.getOrAwaitValue()
            assertTrue(result is ResultWrapper.Success)
            assertTrue((result as ResultWrapper.Success<List<Menu>>).payload?.size == 4)
        }
    }

    @Test
    fun `test getCurrentUser`() {
        runTest {
            val mockUser = mockk<User>(relaxed = true)
            every { userRepo.getCurrentUser() } returns mockUser
            val result = viewModel.getCurrentUser()
            coVerify { userRepo.getCurrentUser() }
            assertEquals(result, mockUser)
        }
    }
}
