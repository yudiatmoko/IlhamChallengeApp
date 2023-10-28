package com.jaws.challengeappilham.data.network.api.model.order

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class OrderRequest(
    @SerializedName("orders")
    val orderItemRequests: List<OrderItemRequest?>?,
    @SerializedName("total")
    val total: Int?,
    @SerializedName("username")
    val username: String?
)
