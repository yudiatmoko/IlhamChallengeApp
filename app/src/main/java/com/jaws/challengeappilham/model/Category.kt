package com.jaws.challengeappilham.model

import java.util.UUID

data class Category(
    val id: String = UUID.randomUUID().toString(),
    val catName: String,
    val catImgSrc: Int,
)