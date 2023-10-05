package com.jaws.challengeappilham.presentation.fragmenthomepage

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.jaws.challengeappilham.data.repository.MenuRepository
import com.jaws.challengeappilham.model.Menu
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map

class HomeViewModel(private val repo: MenuRepository) : ViewModel() {

    val menuData : LiveData<List<Menu>>
        get() = repo.getMenus().map {
            it.payload ?: emptyList()
        }.asLiveData(Dispatchers.IO)
}