package com.jaws.challengeappilham.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.core.view.marginBottom
import androidx.core.view.marginTop
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.google.firebase.auth.FirebaseAuth
import com.jaws.challengeappilham.R
import com.jaws.challengeappilham.data.local.datastore.UserPreferenceDataSourceImpl
import com.jaws.challengeappilham.data.local.datastore.appDataStore
import com.jaws.challengeappilham.data.network.api.datasource.RestaurantApiDataSourceImpl
import com.jaws.challengeappilham.data.network.api.service.RestaurantService
import com.jaws.challengeappilham.data.network.firebase.auth.FirebaseAuthDataSourceImpl
import com.jaws.challengeappilham.data.repository.MenuRepository
import com.jaws.challengeappilham.data.repository.MenuRepositoryImpl
import com.jaws.challengeappilham.data.repository.UserRepository
import com.jaws.challengeappilham.data.repository.UserRepositoryImpl
import com.jaws.challengeappilham.databinding.FragmentHomePageBinding
import com.jaws.challengeappilham.model.Menu
import com.jaws.challengeappilham.presentation.home.category.CategoryListAdapter
import com.jaws.challengeappilham.presentation.home.menu.MenuListAdapter
import com.jaws.challengeappilham.presentation.main.MainViewModel
import com.jaws.challengeappilham.presentation.menudetail.MenuDetailActivity
import com.jaws.challengeappilham.utils.GenericViewModelFactory
import com.jaws.challengeappilham.utils.PreferenceDataStoreHelperImpl
import com.jaws.challengeappilham.utils.proceedWhen

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomePageBinding

    private val menuAdapter: MenuListAdapter by lazy {
        MenuListAdapter(AdapterLayoutMode.LINEAR){
            menu: Menu -> navigateToDetail(menu)
        }
    }

    private val categoryAdapter: CategoryListAdapter by lazy {
        CategoryListAdapter{
            homeViewModel.getMenus(it.slug)
        }
    }

    private val viewModel: MainViewModel by viewModels{
        val dataStore =  this.requireContext().appDataStore
        val dataStoreHelper = PreferenceDataStoreHelperImpl(dataStore)
        val userPreferenceDataSource = UserPreferenceDataSourceImpl(dataStoreHelper)
        GenericViewModelFactory.create(MainViewModel(userPreferenceDataSource))
    }


    private val homeViewModel: HomeViewModel by viewModels {
        val chucker = ChuckerInterceptor(requireContext().applicationContext)
        val service = RestaurantService.invoke(chucker)
        val firebaseAuth = FirebaseAuth.getInstance()
        val menuDataSource = RestaurantApiDataSourceImpl(service)
        val userDataSource = FirebaseAuthDataSourceImpl(firebaseAuth)
        val menuRepo: MenuRepository = MenuRepositoryImpl(menuDataSource)
        val userRepo: UserRepository = UserRepositoryImpl(userDataSource)
        GenericViewModelFactory.create(HomeViewModel(menuRepo,userRepo))
    }

    private fun navigateToDetail(menu: Menu) {
        MenuDetailActivity.startActivity(requireContext(), menu)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomePageBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerViewCategory()
        setRecyclerViewMenu()
        setupSwitch()
        getData()
        setUsername()
    }

    private fun setUsername() {
        val fullName = homeViewModel.getCurrentUser()?.fullName
        val split = fullName?.split(" ")
        val firstName = split?.get(0)
        binding.tvGreetingText.setText("Halo, ${firstName}!")
    }

    private fun getData() {
        homeViewModel.getMenus()
        homeViewModel.getCategories()
    }

    private fun setObserveDataMenu() {
        homeViewModel.menus.observe(viewLifecycleOwner){
            it.proceedWhen(
                doOnSuccess = { result ->
                    binding.clMenuListTitle.isVisible = true
                    binding.layoutStateM.root.isVisible = false
                    binding.layoutStateM.pbLoading.isVisible = false
                    binding.layoutStateM.tvError.isVisible = false
                    binding.rvMenu.isVisible = true
                    result.payload?.let {
                        menuAdapter.setData(it)
                    }
                },
                doOnLoading = {
                    binding.clMenuListTitle.isVisible = false
                    binding.layoutStateM.root.isVisible = true
                    binding.layoutStateM.pbLoading.isVisible = true
                    binding.layoutStateM.tvError.isVisible = false
                    binding.rvMenu.isVisible = false
                },
                doOnError = { err ->
                    binding.layoutStateM.root.isVisible = true
                    binding.layoutStateM.pbLoading.isVisible = false
                    binding.layoutStateM.tvError.isVisible = true
                    binding.layoutStateM.tvError.text = err.exception?.message.orEmpty()
                    binding.rvMenu.isVisible = false
                }, doOnEmpty = {
                    binding.layoutStateM.root.isVisible = true
                    binding.layoutStateM.pbLoading.isVisible = false
                    binding.layoutStateM.tvError.isVisible = true
                    binding.layoutStateM.tvError.text = getString(R.string.no_data_text)
                    binding.rvMenu.isVisible = false
                }
            )
        }
    }

    private fun setRecyclerViewMenu() {
        val span = if(menuAdapter.adapterLayoutMode == AdapterLayoutMode.LINEAR) 1 else 2
        binding.rvMenu.apply {
            layoutManager = GridLayoutManager(requireContext(),span)
            adapter = this@HomeFragment.menuAdapter
        }
        setObserveDataMenu()
    }

    private fun setupSwitch() {
        viewModel.userLinearLayoutLiveData.observe(viewLifecycleOwner){
            binding.switchListGrid.isChecked = it
        }

        binding.switchListGrid.setOnCheckedChangeListener { _, isUsingLinear ->
            viewModel.setLinearLayoutPref(isUsingLinear)
            (binding.rvMenu.layoutManager as GridLayoutManager).spanCount = if (isUsingLinear) 2 else 1
            menuAdapter.adapterLayoutMode = if(isUsingLinear) AdapterLayoutMode.GRID else AdapterLayoutMode.LINEAR
            setObserveDataMenu()
        }
    }

    private fun setRecyclerViewCategory() {
        binding.rvCategory.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            adapter = categoryAdapter
        }
        setObserveDataCategory()
    }

    private fun setObserveDataCategory() {
        homeViewModel.categories.observe(viewLifecycleOwner){
            it.proceedWhen(
                doOnSuccess = { result ->
                    binding.clCategoryTitle.isVisible = true
                    binding.layoutStateC.root.isVisible = false
                    binding.layoutStateC.pbLoading.isVisible = false
                    binding.layoutStateC.tvError.isVisible = false
                    binding.rvCategory.isVisible = true
                    result.payload?.let {
                        categoryAdapter.setData(it)
                    }
                },
                doOnLoading = {
                    binding.clCategoryTitle.isVisible = false
                    binding.layoutStateC.root.isVisible = true
                    binding.layoutStateC.pbLoading.isVisible = false
                    binding.layoutStateC.tvError.isVisible = false
                    binding.rvCategory.isVisible = false
                },
                doOnError = { err ->
                    binding.layoutStateC.root.isVisible = true
                    binding.layoutStateC.pbLoading.isVisible = false
                    binding.layoutStateC.tvError.isVisible = true
                    binding.layoutStateC.tvError.text = err.exception?.message.orEmpty()
                    binding.rvCategory.isVisible = false
                }, doOnEmpty = {
                    binding.layoutStateC.root.isVisible = true
                    binding.layoutStateC.pbLoading.isVisible = false
                    binding.layoutStateC.tvError.isVisible = true
                    binding.layoutStateC.tvError.text = getString(R.string.no_data_text)
                    binding.rvCategory.isVisible = false
                }
            )
        }
    }
}