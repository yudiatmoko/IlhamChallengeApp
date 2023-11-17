package com.jaws.challengeappilham.presentation.splashscreen

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jaws.challengeappilham.data.repository.UserRepository
import com.jaws.challengeappilham.tools.MainCoroutineRule
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.spyk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class SplashScreenViewModelTest {

    @MockK
    lateinit var repo: UserRepository

    private lateinit var viewModel: SplashScreenViewModel

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
        viewModel = spyk(SplashScreenViewModel(repo))
        coEvery { repo.isLoggedIn() } returns true
    }

    @Test
    fun `test is logged in`() {
        viewModel.isUserLoggedIn()
        coVerify { repo.isLoggedIn() }
    }
}
