package com.jaws.challengeappilham.data.local.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.jaws.challengeappilham.data.local.database.entity.MenuEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MenuDao {
    @Query("SELECT * FROM MENUS")
    fun getAllMenus(): Flow<List<MenuEntity>>

    @Query("SELECT * FROM MENUS WHERE id == :id")
    fun getMenuById(id: Int): Flow<MenuEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMenu(product: List<MenuEntity>)

    @Delete
    suspend fun deleteMenu(product: MenuEntity): Int

    @Update
    suspend fun updateMenu(product: MenuEntity): Int
}