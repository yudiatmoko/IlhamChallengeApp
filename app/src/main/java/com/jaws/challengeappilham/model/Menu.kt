package com.jaws.challengeappilham.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
data class Menu(
    val id : String = UUID.randomUUID().toString(),
    val menuImg: Int,
    val menuName: String,
    val menuPrice: Double,
    val menuDesc: String,
) : Parcelable