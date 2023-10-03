package com.jaws.challengeappilham.data.local.database.mapper

import com.jaws.challengeappilham.data.local.database.entity.CartEntity
import com.jaws.challengeappilham.data.local.database.relation.CartMenuRelation
import com.jaws.challengeappilham.model.Cart
import com.jaws.challengeappilham.model.CartMenu

fun CartEntity?.toCart() = Cart(
    id = this?.id ?: 0,
    menuId = this?.menuId ?: 0,
    itemQuantity = this?.itemQuantity ?: 0,
    itemNotes = this?.itemNotes.orEmpty()
)

fun Cart?.toCartEntity() = CartEntity(
    id = this?.id,
    menuId = this?.menuId ?: 0,
    itemQuantity = this?.itemQuantity ?: 0,
    itemNotes = this?.itemNotes.orEmpty()
)

fun List<CartEntity?>.toCartList() = this.map { it.toCart() }

fun CartMenuRelation.toCartMenu() = CartMenu(
    cart = this.cart.toCart(),
    menu = this.menu.toMenu()
)

fun List<CartMenuRelation>.toCartMenuList() = this.map { it.toCartMenu() }