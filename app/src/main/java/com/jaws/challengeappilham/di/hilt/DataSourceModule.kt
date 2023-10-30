package com.jaws.challengeappilham.di.hilt

import com.google.firebase.auth.FirebaseAuth
import com.jaws.challengeappilham.data.local.database.dao.CartDao
import com.jaws.challengeappilham.data.local.database.datasource.CartDataSource
import com.jaws.challengeappilham.data.local.database.datasource.CartDatabaseDataSource
import com.jaws.challengeappilham.data.local.datastore.UserPreferenceDataSource
import com.jaws.challengeappilham.data.local.datastore.UserPreferenceDataSourceImpl
import com.jaws.challengeappilham.data.network.api.datasource.RestaurantApiDataSource
import com.jaws.challengeappilham.data.network.api.datasource.RestaurantApiDataSourceImpl
import com.jaws.challengeappilham.data.network.api.service.RestaurantService
import com.jaws.challengeappilham.data.network.firebase.auth.FirebaseAuthDataSource
import com.jaws.challengeappilham.data.network.firebase.auth.FirebaseAuthDataSourceImpl
import com.jaws.challengeappilham.utils.PreferenceDataStoreHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Singleton
    @Provides
    fun provideCartDataSource(cartDao: CartDao): CartDataSource {
        return CartDatabaseDataSource(cartDao)
    }

    @Singleton
    @Provides
    fun provideUserPreferenceDataSource(preferenceDataStoreHelper: PreferenceDataStoreHelper): UserPreferenceDataSource {
        return UserPreferenceDataSourceImpl(preferenceDataStoreHelper)
    }

    @Singleton
    @Provides
    fun provideRestaurantApiDataSource(service: RestaurantService): RestaurantApiDataSource {
        return RestaurantApiDataSourceImpl(service)
    }

    @Singleton
    @Provides
    fun provideFirebaseAuthDataSource(firebaseAuth: FirebaseAuth): FirebaseAuthDataSource {
        return FirebaseAuthDataSourceImpl(firebaseAuth)
    }
}
