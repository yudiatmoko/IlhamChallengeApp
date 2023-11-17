package com.jaws.challengeappilham.presentation.profile

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jaws.challengeappilham.data.repository.UserRepository
import com.jaws.challengeappilham.model.User
import com.jaws.challengeappilham.tools.MainCoroutineRule
import com.jaws.challengeappilham.tools.getOrAwaitValue
import com.jaws.challengeappilham.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.spyk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class ProfileViewModelTest {

    @MockK
    lateinit var repo: UserRepository

    private lateinit var viewModel: ProfileViewModel

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
        viewModel = spyk(ProfileViewModel(repo))
        val updateResult = flow {
            emit(ResultWrapper.Success(true))
        }
        coEvery { repo.getCurrentUser() } returns User("fullname", "url", "email")
        coEvery { repo.sendChangePasswordRequestByEmail() } returns true
        coEvery { repo.doLogout() } returns true
        coEvery { repo.updateProfile(any(), any()) } returns updateResult
    }

    @Test
    fun`get current user`() {
        viewModel.getCurrentUser()
        coVerify { repo.getCurrentUser() }
    }

    @Test
    fun`create change password request`() {
        viewModel.createChangePasswordReq()
        coVerify { repo.sendChangePasswordRequestByEmail() }
    }

    @Test
    fun`do logout`() {
        viewModel.doLogout()
        coVerify { repo.doLogout() }
    }

    @Test
    fun`update profile`() {
        viewModel.updateProfile("full name")
        coVerify { repo.updateProfile(any()) }
    }

    @Test
    fun`test update profile result live data`() {
        runTest {
            viewModel.updateProfile("fullname")
            coVerify { repo.updateProfile(any()) }
            val result = viewModel.updateProfileResult.getOrAwaitValue()
            assert(result is ResultWrapper.Success)
        }
    }
}
