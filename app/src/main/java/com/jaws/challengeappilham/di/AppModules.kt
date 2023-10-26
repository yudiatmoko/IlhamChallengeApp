package com.jaws.challengeappilham.di

import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.google.firebase.auth.FirebaseAuth
import com.jaws.challengeappilham.data.local.database.AppDatabase
import com.jaws.challengeappilham.data.local.database.datasource.CartDataSource
import com.jaws.challengeappilham.data.local.database.datasource.CartDatabaseDataSource
import com.jaws.challengeappilham.data.local.datastore.UserPreferenceDataSource
import com.jaws.challengeappilham.data.local.datastore.UserPreferenceDataSourceImpl
import com.jaws.challengeappilham.data.local.datastore.appDataStore
import com.jaws.challengeappilham.data.network.api.datasource.RestaurantApiDataSource
import com.jaws.challengeappilham.data.network.api.datasource.RestaurantApiDataSourceImpl
import com.jaws.challengeappilham.data.network.api.service.RestaurantService
import com.jaws.challengeappilham.data.network.firebase.auth.FirebaseAuthDataSource
import com.jaws.challengeappilham.data.network.firebase.auth.FirebaseAuthDataSourceImpl
import com.jaws.challengeappilham.data.repository.CartRepository
import com.jaws.challengeappilham.data.repository.CartRepositoryImpl
import com.jaws.challengeappilham.data.repository.MenuRepository
import com.jaws.challengeappilham.data.repository.MenuRepositoryImpl
import com.jaws.challengeappilham.data.repository.UserRepository
import com.jaws.challengeappilham.data.repository.UserRepositoryImpl
import com.jaws.challengeappilham.presentation.cart.CartViewModel
import com.jaws.challengeappilham.presentation.checkout.CheckoutViewModel
import com.jaws.challengeappilham.presentation.home.HomeViewModel
import com.jaws.challengeappilham.presentation.login.LoginViewModel
import com.jaws.challengeappilham.presentation.main.MainViewModel
import com.jaws.challengeappilham.presentation.menudetail.MenuDetailViewModel
import com.jaws.challengeappilham.presentation.profile.ProfileViewModel
import com.jaws.challengeappilham.presentation.register.RegisterViewModel
import com.jaws.challengeappilham.presentation.splashscreen.SplashScreenViewModel
import com.jaws.challengeappilham.utils.AssetWrapper
import com.jaws.challengeappilham.utils.PreferenceDataStoreHelper
import com.jaws.challengeappilham.utils.PreferenceDataStoreHelperImpl
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.dsl.module

object AppModules {

    private val localModule = module{
        single { AppDatabase.getInstance(androidContext()) }
        single { get<AppDatabase>().cartDao() }
        single { androidContext().appDataStore }
        single <PreferenceDataStoreHelper> { PreferenceDataStoreHelperImpl(get()) }
    }

    private val networkModule = module{
        single { ChuckerInterceptor(androidContext()) }
        single { RestaurantService.invoke(get()) }
        single { FirebaseAuth.getInstance()}
    }

    private val dataSourceModule = module{
        single<CartDataSource> { CartDatabaseDataSource(get()) }
        single<RestaurantApiDataSource> { RestaurantApiDataSourceImpl(get()) }
        single<UserPreferenceDataSource> { UserPreferenceDataSourceImpl(get()) }
        single<FirebaseAuthDataSource>{ FirebaseAuthDataSourceImpl(get()) }
    }

    private val repositoryModule = module{
        single<CartRepository> { CartRepositoryImpl(get(),get()) }
        single<MenuRepository> { MenuRepositoryImpl(get()) }
        single<UserRepository> { UserRepositoryImpl(get()) }
    }

    private val utilsModule = module{
        single { AssetWrapper(androidContext()) }
    }

    private val viewModelModule = module{
        viewModelOf(::MainViewModel)
        viewModelOf(::HomeViewModel)
        viewModelOf(::CartViewModel)
        viewModelOf(::SplashScreenViewModel)
        viewModelOf(::LoginViewModel)
        viewModelOf(::RegisterViewModel)
        viewModelOf(::ProfileViewModel)
        viewModelOf(::CheckoutViewModel)
        viewModel{MenuDetailViewModel(get(), get()) }
    }

    val modules : List<Module> = listOf(
        localModule, networkModule,
        dataSourceModule, repositoryModule,
        viewModelModule, utilsModule
    )
}