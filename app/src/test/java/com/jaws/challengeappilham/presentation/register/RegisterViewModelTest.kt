package com.jaws.challengeappilham.presentation.register

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
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class RegisterViewModelTest {

    @MockK
    lateinit var repo: UserRepository

    private lateinit var viewModel: RegisterViewModel

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
        viewModel = spyk(RegisterViewModel(repo))
        val updateResult = flow {
            emit(ResultWrapper.Success(true))
        }
        coEvery { repo.doRegister(any(), any(), any()) } returns updateResult
    }

    @Test
    fun `test do register`() {
        viewModel.doRegister("full name", "email", "password")
        coVerify { repo.doRegister(any(), any(), any()) }
    }

    @Test
    fun `register result live data`() {
        runTest {
            viewModel.doRegister("full name", "email", "password")
            coVerify { repo.doRegister(any(), any(), any()) }
            val result = viewModel.registerResult.getOrAwaitValue()
            TestCase.assertEquals(result.payload, true)
        }
    }
}
