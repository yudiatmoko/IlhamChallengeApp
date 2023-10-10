package com.jaws.challengeappilham.presentation.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.jaws.challengeappilham.R
import com.jaws.challengeappilham.data.dummy.DummyCategoryDataSource
import com.jaws.challengeappilham.data.dummy.DummyCategoryDataSourceImpl
import com.jaws.challengeappilham.data.local.database.AppDatabase
import com.jaws.challengeappilham.data.local.database.datasource.MenuDatabaseDataSource
import com.jaws.challengeappilham.data.local.datastore.UserPreferenceDataSourceImpl
import com.jaws.challengeappilham.data.local.datastore.appDataStore
import com.jaws.challengeappilham.data.repository.MenuRepository
import com.jaws.challengeappilham.data.repository.MenuRepositoryImpl
import com.jaws.challengeappilham.databinding.FragmentHomePageBinding
import com.jaws.challengeappilham.model.Menu
import com.jaws.challengeappilham.presentation.menudetail.MenuDetailActivity
import com.jaws.challengeappilham.presentation.home.category.CategoryListAdapter
import com.jaws.challengeappilham.presentation.home.menu.MenuListAdapter
import com.jaws.challengeappilham.presentation.main.MainViewModel
import com.jaws.challengeappilham.utils.GenericViewModelFactory
import com.jaws.challengeappilham.utils.PreferenceDataStoreHelperImpl
import com.jaws.challengeappilham.utils.proceedWhen

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomePageBinding

    private val dummyCategoryDataSource : DummyCategoryDataSource by lazy { DummyCategoryDataSourceImpl() }

    private val adapterMenu: MenuListAdapter by lazy {
        MenuListAdapter(AdapterLayoutMode.LINEAR){
            menu: Menu -> navigateToDetail(menu)
        }
    }

    private val adapterCategory: CategoryListAdapter by lazy {
        CategoryListAdapter()
    }

    private val viewModel: MainViewModel by viewModels{
        val dataStore =  this.requireContext().appDataStore
        val dataStoreHelper = PreferenceDataStoreHelperImpl(dataStore)
        val userPreferenceDataSource = UserPreferenceDataSourceImpl(dataStoreHelper)
        GenericViewModelFactory.create(MainViewModel(userPreferenceDataSource))
    }

    private val homeViewModel: HomeViewModel by viewModels {
        val cds: DummyCategoryDataSource = DummyCategoryDataSourceImpl()
        val database = AppDatabase.getInstance(requireContext())
        val menuDao = database.menuDao()
        val menuDataSource = MenuDatabaseDataSource(menuDao)
        val repo: MenuRepository = MenuRepositoryImpl(menuDataSource, cds)
        GenericViewModelFactory.create(HomeViewModel(repo))
    }

    private fun navigateToDetail(menu: Menu) {
//        val action = FragmentHomePageDirections.actionFragmentHomePageToFragmentMenuDetail(menu)
//        findNavController().navigate(action)
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
    }

    private fun setObserveData() {
        homeViewModel.menuData.observe(viewLifecycleOwner){
            it.proceedWhen(
                doOnSuccess = { result ->
                    binding.layoutState.root.isVisible = false
                    binding.layoutState.pbLoading.isVisible = false
                    binding.layoutState.tvError.isVisible = false
                    binding.rvMenu.isVisible = true
                    result.payload?.let {
                        adapterMenu.setData(it)
                    }
                },
                doOnLoading = {
                    binding.layoutState.root.isVisible = true
                    binding.layoutState.pbLoading.isVisible = true
                    binding.layoutState.tvError.isVisible = false
                    binding.rvMenu.isVisible = false
                },
                doOnError = { err ->
                    binding.layoutState.root.isVisible = true
                    binding.layoutState.pbLoading.isVisible = false
                    binding.layoutState.tvError.isVisible = true
                    binding.layoutState.tvError.text = err.exception?.message.orEmpty()
                    binding.rvMenu.isVisible = false
                }, doOnEmpty = {
                    binding.layoutState.root.isVisible = true
                    binding.layoutState.pbLoading.isVisible = false
                    binding.layoutState.tvError.isVisible = true
                    binding.layoutState.tvError.text = getString(R.string.no_data_text)
                    binding.rvMenu.isVisible = false
                }
            )
        }
    }

    private fun setRecyclerViewMenu() {
        val span = if(adapterMenu.adapterLayoutMode == AdapterLayoutMode.LINEAR) 1 else 2
        binding.rvMenu.apply {
            layoutManager = GridLayoutManager(requireContext(),span)
            adapter = this@HomeFragment.adapterMenu
        }
        setObserveData()
    }

    private fun setupSwitch() {
        viewModel.userLinearLayoutLiveData.observe(viewLifecycleOwner){
            binding.switchListGrid.isChecked = it
        }

        binding.switchListGrid.setOnCheckedChangeListener { _, isUsingLinear ->
            viewModel.setLinearLayoutPref(isUsingLinear)
            (binding.rvMenu.layoutManager as GridLayoutManager).spanCount = if (isUsingLinear) 2 else 1
            adapterMenu.adapterLayoutMode = if(isUsingLinear) AdapterLayoutMode.GRID else AdapterLayoutMode.LINEAR
            setObserveData()
        }
    }

    private fun setRecyclerViewCategory() {
        binding.rvCategory.apply {
            layoutManager = FlexboxLayoutManager(requireContext())
            (layoutManager as FlexboxLayoutManager).flexDirection = FlexDirection.ROW
            (layoutManager as FlexboxLayoutManager).justifyContent = JustifyContent.SPACE_BETWEEN
            adapter = adapterCategory
            adapterCategory.setData(dummyCategoryDataSource.getCategoryData())
        }
    }
}