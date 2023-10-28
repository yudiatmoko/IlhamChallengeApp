package com.jaws.challengeappilham.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Menu(
    val id: Int? = null,
    val restaurantAddress: String,
    val detail: String,
    val price: Double,
    val formattedPrice: String,
    val imageUrl: String,
    val name: String
) : Parcelable
