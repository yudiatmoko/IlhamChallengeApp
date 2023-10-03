package com.jaws.challengeappilham.data.local.database.mapper

import com.jaws.challengeappilham.data.local.database.entity.MenuEntity
import com.jaws.challengeappilham.model.Menu

fun MenuEntity?.toMenu() = Menu(
    id = this?.id ?: 0,
    menuName = this?.menuName.orEmpty(),
    menuPrice = this?.menuPrice ?: 0.0,
    menuDesc = this?.menuDesc.orEmpty(),
    menuImg = this?.menuImg.orEmpty(),
)

fun Menu?.toMenuEntity() = MenuEntity(
    id = this?.id ?: 0,
    menuName = this?.menuName.orEmpty(),
    menuPrice = this?.menuPrice ?: 0.0,
    menuDesc = this?.menuDesc.orEmpty(),
    menuImg = this?.menuImg.orEmpty(),
).apply {
    this@toMenuEntity?.id?.let {
        this.id = this@toMenuEntity.id
    }
}

fun List<MenuEntity?>.toMenuList() = this.map { it.toMenu() }
fun List<Menu?>.toMenuEntity() = this.map { it.toMenuEntity() }
