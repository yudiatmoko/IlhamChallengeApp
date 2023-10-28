package com.jaws.challengeappilham.data.repository

import android.net.Uri
import com.jaws.challengeappilham.data.network.firebase.auth.FirebaseAuthDataSource
import com.jaws.challengeappilham.model.User
import com.jaws.challengeappilham.model.toUser
import com.jaws.challengeappilham.utils.ResultWrapper
import com.jaws.challengeappilham.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun doLogin(email: String, password: String): Flow<ResultWrapper<Boolean>>

    suspend fun doRegister(
        fullName: String,
        email: String,
        password: String
    ): Flow<ResultWrapper<Boolean>>

    fun doLogout(): Boolean

    fun isLoggedIn(): Boolean

    fun getCurrentUser(): User?

    suspend fun updateProfile(
        fullName: String? = null,
        photoUri: Uri? = null
    ): Flow<ResultWrapper<Boolean>>

    suspend fun updatePassword(newPassword: String): Flow<ResultWrapper<Boolean>>

    suspend fun updateEmail(newEmail: String): Flow<ResultWrapper<Boolean>>

    fun sendChangePasswordRequestByEmail(): Boolean
}

class UserRepositoryImpl(private val dataSource: FirebaseAuthDataSource) : UserRepository {
    override suspend fun doLogin(email: String, password: String): Flow<ResultWrapper<Boolean>> {
        return proceedFlow { dataSource.doLogin(email, password) }
    }

    override suspend fun doRegister(
        fullName: String,
        email: String,
        password: String
    ): Flow<ResultWrapper<Boolean>> {
        return proceedFlow { dataSource.doRegister(fullName, email, password) }
    }

    override fun doLogout(): Boolean {
        return dataSource.doLogout()
    }

    override fun isLoggedIn(): Boolean {
        return dataSource.isLoggedIn()
    }

    override fun getCurrentUser(): User? {
        return dataSource.getCurrentUser().toUser()
    }

    override suspend fun updateProfile(
        fullName: String?,
        photoUri: Uri?
    ): Flow<ResultWrapper<Boolean>> {
        return proceedFlow { dataSource.updateProfile(fullName, photoUri) }
    }

    override suspend fun updatePassword(newPassword: String): Flow<ResultWrapper<Boolean>> {
        return proceedFlow { dataSource.updatePassword(newPassword) }
    }

    override suspend fun updateEmail(newEmail: String): Flow<ResultWrapper<Boolean>> {
        return proceedFlow { dataSource.updateEmail(newEmail) }
    }

    override fun sendChangePasswordRequestByEmail(): Boolean {
        return dataSource.sendChangePasswordRequestByEmail()
    }
}
