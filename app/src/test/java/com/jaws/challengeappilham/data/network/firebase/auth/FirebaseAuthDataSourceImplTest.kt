package com.jaws.challengeappilham.data.network.firebase.auth

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class FirebaseAuthDataSourceImplTest {

    @MockK
    lateinit var firebaseAuth: FirebaseAuth

    private lateinit var firebaseAuthDataSource: FirebaseAuthDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        firebaseAuthDataSource = FirebaseAuthDataSourceImpl(firebaseAuth)
    }

    @Test
    fun `test login`() {
        every { firebaseAuth.signInWithEmailAndPassword(any(), any()) } returns mockTaskAuthResult()
        runTest {
            val result = firebaseAuthDataSource.doLogin("email", "password")
            assertEquals(result, true)
            verify { firebaseAuth.signInWithEmailAndPassword(any(), any()) }
        }
    }

    @Test
    fun `test isLoggedIn`() {
        every { firebaseAuth.currentUser } returns mockk(relaxed = true)
        runTest {
            val result = firebaseAuthDataSource.isLoggedIn()
            assertEquals(result, true)
            verify { firebaseAuth.currentUser }
        }
    }

    @Test
    fun `test getCurrentUser`() {
        val mockUser = mockk<FirebaseUser>(relaxed = true)
        every { firebaseAuth.currentUser } returns mockUser
        runTest {
            val result = firebaseAuthDataSource.getCurrentUser()
            assertEquals(result, mockUser)
            verify { firebaseAuth.currentUser }
        }
    }

    @Test
    fun `test update password`() {
        every { firebaseAuth.currentUser?.updatePassword(any()) } returns mockTaskVoid()
        runTest {
            val result = firebaseAuthDataSource.updatePassword("new password")
            assertEquals(result, true)
            verify { firebaseAuth.currentUser?.updatePassword(any()) }
        }
    }

    @Test
    fun `test update email`() {
        every { firebaseAuth.currentUser?.updateEmail(any()) } returns mockTaskVoid()
        runTest {
            val result = firebaseAuthDataSource.updateEmail("new email")
            assertEquals(result, true)
            verify { firebaseAuth.currentUser?.updateEmail(any()) }
        }
    }

    @Test
    fun `test send change password request by email`() {
        every { firebaseAuth.sendPasswordResetEmail(any()) } returns mockTaskVoid()
        every { firebaseAuth.currentUser?.email } returns "email"
        runTest {
            val result = firebaseAuthDataSource.sendChangePasswordRequestByEmail()
            assertEquals(result, true)
            verify { firebaseAuth.sendPasswordResetEmail(any()) }
        }
    }

    private fun mockTaskVoid(e: Exception? = null): Task<Void> {
        val task: Task<Void> = mockk(relaxed = true)
        every { task.isComplete } returns true
        every { task.exception } returns e
        every { task.isCanceled } returns false
        val relaxedVoid: Void = mockk(relaxed = true)
        every { task.result } returns relaxedVoid
        return task
    }

    private fun mockTaskAuthResult(e: Exception? = null): Task<AuthResult> {
        val task: Task<AuthResult> = mockk(relaxed = true)
        every { task.isComplete } returns true
        every { task.exception } returns e
        every { task.isCanceled } returns false
        val relaxedAuthResult: AuthResult = mockk(relaxed = true)
        every { task.result } returns relaxedAuthResult
        every { task.result.user } returns mockk(relaxed = true)
        return task
    }
}
