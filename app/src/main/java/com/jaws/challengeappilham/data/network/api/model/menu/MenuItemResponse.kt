package com.jaws.challengeappilham.data.network.api.model.menu


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep
import com.jaws.challengeappilham.model.Menu

@Keep
data class MenuItemResponse(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("alamat_resto")
    val restaurantAddress: String?,
    @SerializedName("detail")
    val detail: String?,
    @SerializedName("harga")
    val price: Double?,
    @SerializedName("harga_format")
    val formattedPrice: String?,
    @SerializedName("image_url")
    val imageUrl: String?,
    @SerializedName("nama")
    val name: String?
)

fun MenuItemResponse.toMenu() = Menu(
    id = this.id ?: 0,
    price = this.price ?: 0.0,
    name = this.name.orEmpty(),
    formattedPrice = this.formattedPrice.orEmpty(),
    detail = this.detail.orEmpty(),
    restaurantAddress = this.restaurantAddress.orEmpty(),
    imageUrl = this.imageUrl.orEmpty()
)

fun Collection<MenuItemResponse>.toMenuList() = this.map {
    it.toMenu()
}