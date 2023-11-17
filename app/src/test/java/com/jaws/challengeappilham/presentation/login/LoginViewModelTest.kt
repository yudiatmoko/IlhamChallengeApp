package com.jaws.challengeappilham.presentation.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jaws.challengeappilham.data.repository.UserRepository
import com.jaws.challengeappilham.tools.MainCoroutineRule
import com.jaws.challengeappilham.tools.getOrAwaitValue
import com.jaws.challengeappilham.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.spyk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class LoginViewModelTest {

    @MockK
    lateinit var repo: UserRepository

    private lateinit var viewModel: LoginViewModel

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
        viewModel = spyk(LoginViewModel(repo))
        val updateResult = flow {
            emit(ResultWrapper.Success(true))
        }
        coEvery { repo.doLogin(any(), any()) } returns updateResult
    }

    @Test
    fun `test do login`() {
        viewModel.doLogin("email", "password")
        coVerify { repo.doLogin(any(), any()) }
    }

    @Test
    fun `login result live data`() {
        runTest {
            viewModel.doLogin("email", "password")
            coVerify { repo.doLogin(any(), any()) }
            val result = viewModel.loginResult.getOrAwaitValue()
            assertEquals(result.payload, true)
        }
    }
}
