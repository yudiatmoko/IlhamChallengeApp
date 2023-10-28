package com.jaws.challengeappilham.model

import com.jaws.challengeappilham.data.network.api.model.order.OrderItemRequest

data class OrderItem(
    val name: String,
    val notes: String,
    val price: Int,
    val qty: Int
)

fun OrderItem.toOrderItemRequest() = OrderItemRequest(
    name = this.name,
    notes = this.notes,
    price = this.price,
    qty = this.qty
)
