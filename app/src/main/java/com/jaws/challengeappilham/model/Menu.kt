package com.jaws.challengeappilham.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
data class Menu(
    val id : Int,
    val menuImg: String,
    val menuName: String,
    val menuPrice: Double,
    val menuDesc: String,
) : Parcelable