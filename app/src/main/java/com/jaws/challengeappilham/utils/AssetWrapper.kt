package com.jaws.challengeappilham.utils

import android.content.Context
import androidx.annotation.StringRes


/*
Hi, Code Enthusiast!
https://github.com/yudiatmoko
*/

class AssetWrapper(private val appContext: Context) {
    fun getString(@StringRes id: Int): String{
        return appContext.getString(id)
    }


}