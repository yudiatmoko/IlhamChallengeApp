package com.jaws.challengeappilham.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.jaws.challengeappilham.data.repository.MenuRepository
import com.jaws.challengeappilham.model.Menu
import com.jaws.challengeappilham.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers

class HomeViewModel(private val repo: MenuRepository) : ViewModel() {

    val menuData : LiveData<ResultWrapper<List<Menu>>>
        get() = repo.getMenus().asLiveData(Dispatchers.IO)

}
