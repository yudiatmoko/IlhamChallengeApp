package com.jaws.challengeappilham.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.jaws.challengeappilham.data.local.datastore.UserPreferenceDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userPreferenceDataSource: UserPreferenceDataSource
) : ViewModel() {
    val userLinearLayoutLiveData = userPreferenceDataSource.getUserLayoutPrefFlow().asLiveData(Dispatchers.IO)

    fun setLinearLayoutPref(isUsingLinear: Boolean) {
        viewModelScope.launch {
            userPreferenceDataSource.setUserLayoutPref(isUsingLinear)
        }
    }
}
