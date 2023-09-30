package com.jaws.challengeappilham.data

import com.jaws.challengeappilham.R
import com.jaws.challengeappilham.model.Category

interface CategoryDataSource {
    fun getCategoryData() : List<Category>
}

class CategoryDataSourceImpl() : CategoryDataSource{
    override fun getCategoryData(): List<Category> {
        return mutableListOf(
            Category(
                catName = "Nasi",
                catImgSrc = R.drawable.img_rice
            ),
            Category(
                catName = "Mie",
                catImgSrc = R.drawable.img_noodle
            ),
            Category(
                catName = "Snack",
                catImgSrc = R.drawable.img_snack
            ),
            Category(
                catName = "Minuman",
                catImgSrc = R.drawable.img_drink
            ),
        )
    }

}