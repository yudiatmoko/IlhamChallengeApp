package com.jaws.challengeappilham.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jaws.challengeappilham.R
import com.jaws.challengeappilham.databinding.FragmentHomePageBinding
import com.jaws.challengeappilham.model.Menu
import com.jaws.challengeappilham.presentation.home.category.CategoryListAdapter
import com.jaws.challengeappilham.presentation.home.menu.MenuListAdapter
import com.jaws.challengeappilham.presentation.main.MainViewModel
import com.jaws.challengeappilham.presentation.menudetail.MenuDetailActivity
import com.jaws.challengeappilham.utils.proceedWhen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomePageBinding

    private val homeViewModel: HomeViewModel by viewModels()

    private val mainViewModel: MainViewModel by viewModels()

    private val menuAdapter: MenuListAdapter by lazy {
        MenuListAdapter(AdapterLayoutMode.LINEAR) {
                menu: Menu ->
            navigateToDetail(menu)
        }
    }

    private val categoryAdapter: CategoryListAdapter by lazy {
        CategoryListAdapter {
            homeViewModel.getMenus(it.slug)
        }
    }

    private fun navigateToDetail(menu: Menu) {
        MenuDetailActivity.startActivity(requireContext(), menu)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomePageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerViewCategory()
        setRecyclerViewMenu()
        setUsername()
        setupSwitch()
        getData()
        observeMenuLayout()
    }

    private fun setUsername() {
        val fullName = homeViewModel.getCurrentUser()?.fullName
        val split = fullName?.split(" ")
        val firstName = split?.get(0)
        binding.tvGreetingText.setText(getString(R.string.halo, firstName))
    }

    private fun getData() {
        homeViewModel.getMenus()
        homeViewModel.getCategories()
    }

    private fun setObserveDataMenu() {
        homeViewModel.menus.observe(viewLifecycleOwner) {
            it.proceedWhen(
                doOnSuccess = { result ->
                    binding.layoutStateM.root.isVisible = false
                    binding.layoutStateM.pbLoading.isVisible = false
                    binding.layoutStateM.tvError.isVisible = false
                    binding.rvMenu.isVisible = true
                    result.payload?.let {
                        menuAdapter.setData(it)
                    }
                },
                doOnLoading = {
                    binding.clMenuListTitle.isVisible = true
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
                },
                doOnEmpty = {
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
        val span = if (menuAdapter.adapterLayoutMode == AdapterLayoutMode.LINEAR) 1 else 2
        binding.rvMenu.apply {
            layoutManager = GridLayoutManager(requireContext(), span)
            adapter = this@HomeFragment.menuAdapter
        }
        setObserveDataMenu()
    }

    private fun observeMenuLayout() {
        mainViewModel.userLinearLayoutLiveData.observe(viewLifecycleOwner) { usingGrid ->
            binding.ivGrid.isVisible = !usingGrid
            binding.ivList.isVisible = usingGrid
            (binding.rvMenu.layoutManager as GridLayoutManager).spanCount = if (usingGrid) 2 else 1
            menuAdapter.adapterLayoutMode = if (usingGrid) AdapterLayoutMode.GRID else AdapterLayoutMode.LINEAR
            menuAdapter.refreshList()
        }
    }

    private fun setupSwitch() {
        binding.ivGrid.setOnClickListener {
            mainViewModel.setLinearLayoutPref(isUsingLinear = true)
        }
        binding.ivList.setOnClickListener {
            mainViewModel.setLinearLayoutPref(isUsingLinear = false)
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
        homeViewModel.categories.observe(viewLifecycleOwner) {
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
                    binding.clCategoryTitle.isVisible = true
                    binding.layoutStateC.root.isVisible = true
                    binding.layoutStateC.pbLoading.isVisible = true
                    binding.layoutStateC.tvError.isVisible = false
                    binding.rvCategory.isVisible = false
                },
                doOnError = { err ->
                    binding.layoutStateC.root.isVisible = true
                    binding.layoutStateC.pbLoading.isVisible = false
                    binding.layoutStateC.tvError.isVisible = true
                    binding.layoutStateC.tvError.text = err.exception?.message.orEmpty()
                    binding.rvCategory.isVisible = false
                },
                doOnEmpty = {
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
