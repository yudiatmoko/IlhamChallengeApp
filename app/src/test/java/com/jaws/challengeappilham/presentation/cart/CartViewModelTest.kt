package com.jaws.challengeappilham.presentation.cart

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jaws.challengeappilham.data.repository.CartRepository
import com.jaws.challengeappilham.tools.MainCoroutineRule
import com.jaws.challengeappilham.tools.getOrAwaitValue
import com.jaws.challengeappilham.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.spyk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class CartViewModelTest {

    @MockK
    lateinit var repo: CartRepository

    private lateinit var viewModel: CartViewModel

    @get:Rule
    val testRule: TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule: TestRule = MainCoroutineRule(
        UnconfinedTestDispatcher()
    )

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        coEvery { repo.getCartList() } returns flow {
            emit(
                ResultWrapper.Success(
                    Pair(
                        listOf(
                            mockk(relaxed = true),
                            mockk(relaxed = true),
                            mockk(relaxed = true)
                        ),
                        127000.0
                    )
                )
            )
        }
        viewModel = spyk(CartViewModel(repo))
        val updateResult = flow {
            emit(ResultWrapper.Success(true))
        }
        coEvery { repo.increaseCart(any()) } returns updateResult
        coEvery { repo.decreaseCart(any()) } returns updateResult
        coEvery { repo.deleteCart(any()) } returns updateResult
        coEvery { repo.setCartNotes(any()) } returns updateResult
    }

    @Test
    fun`test cart list`() {
        val result = viewModel.cartList.getOrAwaitValue()
        assertEquals(result.payload?.first?.size, 3)
        assertEquals(result.payload?.second, 127000.0)
    }

    @Test
    fun `test decrease cart`() {
        viewModel.decreaseCart(mockk())
        coVerify { repo.decreaseCart(any()) }
    }

    @Test
    fun `test increase cart`() {
        viewModel.increaseCart(mockk())
        coVerify { repo.increaseCart(any()) }
    }

    @Test
    fun `test delete cart`() {
        viewModel.deleteCart(mockk())
        coVerify { repo.deleteCart(any()) }
    }

    @Test
    fun `test set cart notes cart`() {
        viewModel.updateNotes(mockk())
        coVerify { repo.setCartNotes(any()) }
    }
}
