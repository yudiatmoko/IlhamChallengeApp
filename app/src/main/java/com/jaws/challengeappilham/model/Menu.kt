package com.jaws.challengeappilham.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
data class Menu(
    val id: Int,
    val restaurantAddress: String,
    val detail: String,
    val price: Double,
    val formattedPrice: String,
    val imageUrl: String,
    val name: String
) : Parcelable