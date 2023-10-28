package com.jaws.challengeappilham.data.local.datastore

import androidx.datastore.preferences.core.booleanPreferencesKey
import com.jaws.challengeappilham.utils.PreferenceDataStoreHelper
import kotlinx.coroutines.flow.Flow

interface UserPreferenceDataSource {
    fun getUserLayoutPrefFlow(): Flow<Boolean>
    suspend fun setUserLayoutPref(isUsingLinear: Boolean)
    suspend fun getUserLayoutPref(): Boolean
}

class UserPreferenceDataSourceImpl(
    private val preferenceDataStoreHelper: PreferenceDataStoreHelper
) : UserPreferenceDataSource {
    override fun getUserLayoutPrefFlow(): Flow<Boolean> {
        return preferenceDataStoreHelper.getPreference(
            PREF_USER_LINEAR_LAYOUT,
            true
        )
    }

    override suspend fun setUserLayoutPref(
        isUsingLinear: Boolean
    ) {
        preferenceDataStoreHelper.putPreference(
            PREF_USER_LINEAR_LAYOUT,
            isUsingLinear
        )
    }

    override suspend fun getUserLayoutPref(): Boolean {
        return preferenceDataStoreHelper.getFirstPreference(
            PREF_USER_LINEAR_LAYOUT,
            true
        )
    }

    companion object {
        val PREF_USER_LINEAR_LAYOUT = booleanPreferencesKey("PREF_USER_LINEAR_LAYOUT")
    }
}
