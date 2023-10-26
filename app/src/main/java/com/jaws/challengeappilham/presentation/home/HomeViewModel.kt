package com.jaws.challengeappilham.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jaws.challengeappilham.R
import com.jaws.challengeappilham.data.repository.MenuRepository
import com.jaws.challengeappilham.data.repository.UserRepository
import com.jaws.challengeappilham.model.Category
import com.jaws.challengeappilham.model.Menu
import com.jaws.challengeappilham.utils.AssetWrapper
import com.jaws.challengeappilham.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(
    private val menuRepo: MenuRepository,
    private val userRepo: UserRepository,
    private val assetWrapper: AssetWrapper
) : ViewModel() {

    private val _categories = MutableLiveData<ResultWrapper<List<Category>>>()
    val categories : LiveData<ResultWrapper<List<Category>>>
        get() = _categories

    private val _menus = MutableLiveData<ResultWrapper<List<Menu>>>()
    val menus : LiveData<ResultWrapper<List<Menu>>>
        get() = _menus

    fun getCategories(){
        viewModelScope.launch(Dispatchers.IO) {
            menuRepo.getCategories().collect{
                _categories.postValue(it)
            }
        }
    }

    fun getMenus(category: String? = null){
        viewModelScope.launch(Dispatchers.IO) {
            menuRepo.getMenus(if(category == assetWrapper.getString(R.string.all)) null else category).collect{
                _menus.postValue(it)
            }
        }
    }
    fun getCurrentUser() = userRepo.getCurrentUser()
}

