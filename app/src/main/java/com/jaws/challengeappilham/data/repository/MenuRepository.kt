package com.jaws.challengeappilham.data.repository

import com.jaws.challengeappilham.data.network.api.datasource.RestaurantApiDataSource
import com.jaws.challengeappilham.data.network.api.model.category.toCategoryList
import com.jaws.challengeappilham.data.network.api.model.menu.toMenuList
import com.jaws.challengeappilham.model.Category
import com.jaws.challengeappilham.model.Menu
import com.jaws.challengeappilham.utils.ResultWrapper
import com.jaws.challengeappilham.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

interface MenuRepository {
    suspend fun getCategories(): Flow<ResultWrapper<List<Category>>>
    suspend fun getMenus(category: String? = null): Flow<ResultWrapper<List<Menu>>>
}

class MenuRepositoryImpl(
    private val apiDataSource: RestaurantApiDataSource
) : MenuRepository {

    override suspend fun getCategories(): Flow<ResultWrapper<List<Category>>> {
        return proceedFlow {
            apiDataSource.getCategories().data?.toCategoryList() ?: emptyList()
        }
    }

    override suspend fun getMenus(category: String?): Flow<ResultWrapper<List<Menu>>> {
        return proceedFlow {
            apiDataSource.getMenus(category).data?.toMenuList() ?: emptyList()
        }
    }
}