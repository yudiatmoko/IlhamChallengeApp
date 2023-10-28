package com.jaws.challengeappilham.data.local.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "carts")
data class CartEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    @ColumnInfo(name = "menu_id")
    var menuId: Int = 0,
    @ColumnInfo(name = "menu_name")
    val menuName: String,
    @ColumnInfo(name = "menu_price")
    val menuPrice: Double,
    @ColumnInfo(name = "menu_img_url")
    val menuImgUrl: String,
    @ColumnInfo(name = "item_quantity")
    var itemQuantity: Int = 0,
    @ColumnInfo(name = "item_notes")
    var itemNotes: String? = null
)
