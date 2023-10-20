package com.jaws.challengeappilham.model

data class Cart(
    var id: Int? = null,
    var menuId : Int = 0,
    var itemQuantity: Int = 0,
    val menuName: String,
    val menuPrice: Double,
    val menuImgUrl: String,
    var itemNotes: String? = null,
)