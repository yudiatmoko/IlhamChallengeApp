package com.jaws.challengeappilham.data.local.database.mapper

import com.jaws.challengeappilham.data.local.database.entity.CartEntity
import com.jaws.challengeappilham.model.Cart

fun CartEntity?.toCart() = Cart(
    id = this?.id ?: 0,
    menuId = this?.menuId ?: 0,
    itemQuantity = this?.itemQuantity ?: 0,
    itemNotes = this?.itemNotes.orEmpty(),
    menuPrice = this?.menuPrice ?: 0.0,
    menuName = this?.menuName.orEmpty(),
    menuImgUrl = this?.menuImgUrl.orEmpty()
)

fun Cart?.toCartEntity() = CartEntity(
    id = this?.id,
    menuId = this?.menuId ?: 0,
    itemQuantity = this?.itemQuantity ?: 0,
    itemNotes = this?.itemNotes.orEmpty(),
    menuPrice = this?.menuPrice ?: 0.0,
    menuName = this?.menuName.orEmpty(),
    menuImgUrl = this?.menuImgUrl.orEmpty()
)

fun List<CartEntity?>.toCartList() = this.map { it.toCart() }
