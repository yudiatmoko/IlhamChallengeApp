package com.jaws.challengeappilham.data.local.database.datasource

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.jaws.challengeappilham.data.local.database.dao.MenuDao
import com.jaws.challengeappilham.data.local.database.entity.MenuEntity
import kotlinx.coroutines.flow.Flow

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
interface MenuDataSource {
    fun getAllMenus(): Flow<List<MenuEntity>>
    fun getMenuById(id: Int): Flow<MenuEntity>
    suspend fun insertMenus(product: List<MenuEntity>)
    suspend fun deleteMenu(product: MenuEntity): Int
    suspend fun updateMenu(product: MenuEntity): Int
}

class MenuDatabaseDataSource(private val dao : MenuDao) : MenuDataSource {
    override fun getAllMenus(): Flow<List<MenuEntity>> {
        return dao.getAllMenus()
    }

    override fun getMenuById(id: Int): Flow<MenuEntity> {
        return dao.getMenuById(id)
    }

    override suspend fun insertMenus(product: List<MenuEntity>) {
        return dao.insertMenu(product)
    }

    override suspend fun deleteMenu(product: MenuEntity): Int {
        return dao.deleteMenu(product)
    }

    override suspend fun updateMenu(product: MenuEntity): Int {
        return dao.updateMenu(product)
    }

}