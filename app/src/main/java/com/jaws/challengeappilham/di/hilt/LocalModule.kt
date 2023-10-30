package com.jaws.challengeappilham.di.hilt

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.jaws.challengeappilham.data.local.database.AppDatabase
import com.jaws.challengeappilham.data.local.database.dao.CartDao
import com.jaws.challengeappilham.data.local.datastore.appDataStore
import com.jaws.challengeappilham.utils.PreferenceDataStoreHelper
import com.jaws.challengeappilham.utils.PreferenceDataStoreHelperImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }

    @Singleton
    @Provides
    fun provideCartDao(appDatabase: AppDatabase): CartDao {
        return appDatabase.cartDao()
    }

    @Singleton
    @Provides
    fun provideAppDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.appDataStore
    }

    @Singleton
    @Provides
    fun providePreferenceDataStoreHelper(preferences: DataStore<Preferences>): PreferenceDataStoreHelper {
        return PreferenceDataStoreHelperImpl(preferences)
    }
}
