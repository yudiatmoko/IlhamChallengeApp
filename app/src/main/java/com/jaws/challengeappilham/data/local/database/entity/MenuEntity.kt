package com.jaws.challengeappilham.data.local.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "menus")
data class MenuEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    @ColumnInfo(name = "menu_name")
    val menuName: String,
    @ColumnInfo(name = "menu_price")
    val menuPrice: Double,
    @ColumnInfo(name = "menu_desc")
    val menuDesc: String,
    @ColumnInfo(name = "menu_img")
    val menuImg: String
)