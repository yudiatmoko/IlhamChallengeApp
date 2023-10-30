package com.jaws.challengeappilham.di.hilt

import com.jaws.challengeappilham.data.local.database.datasource.CartDataSource
import com.jaws.challengeappilham.data.network.api.datasource.RestaurantApiDataSource
import com.jaws.challengeappilham.data.network.firebase.auth.FirebaseAuthDataSource
import com.jaws.challengeappilham.data.repository.CartRepository
import com.jaws.challengeappilham.data.repository.CartRepositoryImpl
import com.jaws.challengeappilham.data.repository.MenuRepository
import com.jaws.challengeappilham.data.repository.MenuRepositoryImpl
import com.jaws.challengeappilham.data.repository.UserRepository
import com.jaws.challengeappilham.data.repository.UserRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideCartRepository(cartDataSource: CartDataSource, restaurantApiDataSource: RestaurantApiDataSource): CartRepository {
        return CartRepositoryImpl(cartDataSource, restaurantApiDataSource)
    }

    @Singleton
    @Provides
    fun provideMenuRepository(restaurantApiDataSource: RestaurantApiDataSource): MenuRepository {
        return MenuRepositoryImpl(restaurantApiDataSource)
    }

    @Singleton
    @Provides
    fun provideUserRepository(firebaseAuthDataSource: FirebaseAuthDataSource): UserRepository {
        return UserRepositoryImpl(firebaseAuthDataSource)
    }
}
