package com.jaws.challengeappilham.data.repository

import com.jaws.challengeappilham.data.dummy.DummyCategoryDataSource
import com.jaws.challengeappilham.data.local.database.datasource.MenuDataSource
import com.jaws.challengeappilham.data.local.database.mapper.toMenuList
import com.jaws.challengeappilham.model.Category
import com.jaws.challengeappilham.model.Menu
import com.jaws.challengeappilham.utils.ResultWrapper
import com.jaws.challengeappilham.utils.proceed
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

interface MenuRepository {
    fun getCategories(): List<Category>
    fun getProducts(): Flow<ResultWrapper<List<Menu>>>
}

class MenuRepositoryImpl(
    private val menuDataSource: MenuDataSource,
    private val dummyCategoryDataSource: DummyCategoryDataSource
) : MenuRepository {

    override fun getCategories(): List<Category> {
        return dummyCategoryDataSource.getCategoryData()
    }

    override fun getProducts(): Flow<ResultWrapper<List<Menu>>> {
        return menuDataSource.getAllMenus().map { proceed { it.toMenuList() } }.onStart {
            emit(ResultWrapper.Loading())
            delay(2000)
        }
    }
}