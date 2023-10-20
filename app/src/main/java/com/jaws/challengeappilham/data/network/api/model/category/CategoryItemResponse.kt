package com.jaws.challengeappilham.data.network.api.model.category


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep
import com.jaws.challengeappilham.model.Category

@Keep
data class CategoryItemResponse(
    @SerializedName("image_url")
    val imageUrl: String?,
    @SerializedName("nama")
    val name: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("slug")
    val slug: String?
)

fun CategoryItemResponse.toCategory() = Category(
    catImgSrc = this.imageUrl.orEmpty(),
    catName = this.name.orEmpty(),
    id = this.id ?: 0,
    slug = this.slug.orEmpty()
)

fun Collection<CategoryItemResponse>.toCategoryList() = this.map {
    it.toCategory()
}