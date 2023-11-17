package com.jaws.challengeappilham.data.local.datastore

import app.cash.turbine.test
import com.jaws.challengeappilham.utils.PreferenceDataStoreHelper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class UserPreferenceDataSourceImplTest {

    @MockK
    lateinit var preferenceDataStoreHelper: PreferenceDataStoreHelper

    private lateinit var userPreferenceDataSource: UserPreferenceDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        userPreferenceDataSource = UserPreferenceDataSourceImpl(preferenceDataStoreHelper)
    }

    @Test
    fun getUserLayoutPrefFlow() {
        runTest {
            coEvery { preferenceDataStoreHelper.getPreference(any(), true) } returns flow { emit(false) }
            userPreferenceDataSource.getUserLayoutPrefFlow().test {
                val itemResult = awaitItem()
                assertEquals(false, itemResult)
                awaitComplete()
            }
        }
    }

    @Test
    fun setUserLayoutPref() {
        runTest {
            coEvery { preferenceDataStoreHelper.putPreference(any(), true) } returns Unit
            val result = userPreferenceDataSource.setUserLayoutPref(true)
            coVerify { preferenceDataStoreHelper.putPreference(any(), true) }
            TestCase.assertEquals(result, Unit)
        }
    }

    @Test
    fun getUserLayoutPref() {
        runTest {
            coEvery { preferenceDataStoreHelper.getFirstPreference(any(), true) } returns false
            val result = userPreferenceDataSource.getUserLayoutPref()
            coVerify { preferenceDataStoreHelper.getFirstPreference(any(), true) }
            TestCase.assertEquals(result, false)
        }
    }
}
