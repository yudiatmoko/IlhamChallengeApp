package com.jaws.challengeappilham.presentation.fragmenthomepage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.jaws.challengeappilham.R
import com.jaws.challengeappilham.data.MenuDataSource
import com.jaws.challengeappilham.data.MenuDataSourceImpl
import com.jaws.challengeappilham.databinding.FragmentHomePageBinding
import com.jaws.challengeappilham.model.Category
import com.jaws.challengeappilham.model.Menu

class FragmentHomePage : Fragment() {

    private lateinit var binding: FragmentHomePageBinding

    private val dataSource : MenuDataSource by lazy { MenuDataSourceImpl() }

    private val adapter: MenuListAdapter by lazy {
        MenuListAdapter(AdapterLayoutMode.LINEAR){
            menu: Menu -> navigateToDetail(menu)
        }
    }

    private fun navigateToDetail(menu: Menu? = null) {
        val action = FragmentHomePageDirections.actionFragmentHomePageToFragmentMenuDetail(menu)
        findNavController().navigate(action)
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
        setModeButton()
    }

    private fun setModeButton() {
        binding.switchListGrid.setOnCheckedChangeListener { _, isChecked ->
            (binding.rvMenu.layoutManager as GridLayoutManager).spanCount = if (isChecked) 2 else 1
            adapter.adapterLayoutMode = if(isChecked) AdapterLayoutMode.GRID else AdapterLayoutMode.LINEAR
            adapter.refreshList()
        }
    }

    private fun setRecyclerViewMenu() {

        val span = if(adapter.adapterLayoutMode == AdapterLayoutMode.LINEAR) 1 else 2
        binding.rvMenu.apply {
            layoutManager = GridLayoutManager(requireContext(),span)
            adapter = this@FragmentHomePage.adapter
        }
        adapter.setData(dataSource.getMenuData())

//        binding.rvMenu.adapter = adapter
//        val layoutManagerMenu = FlexboxLayoutManager(requireContext())
//        layoutManagerMenu.flexDirection = FlexDirection.ROW
//        layoutManagerMenu.justifyContent = JustifyContent.SPACE_BETWEEN
//        binding.rvMenu.layoutManager = layoutManagerMenu
    }



    private fun setRecyclerViewCategory() {

        // Add Category List
        val categoryList = mutableListOf<Category>(
            Category(R.string.nasi, R.drawable.img_rice),
            Category(R.string.mie, R.drawable.img_noodle),
            Category(R.string.snack, R.drawable.img_snack),
            Category(R.string.minuman, R.drawable.img_drink)
        )

        // Create Adapter
        val recyclerViewAdapterCategory = CategoryListAdapter(categoryList)

        // Create Layout Manager
        val layoutManagerCategory = FlexboxLayoutManager(requireContext())
        layoutManagerCategory.flexDirection = FlexDirection.ROW
        layoutManagerCategory.justifyContent = JustifyContent.SPACE_BETWEEN

        // Create RecyclerView
        val recyclerViewCategory = binding.rvCategory

        // Set LayoutManager on RecyclerView
        recyclerViewCategory.layoutManager = layoutManagerCategory

        // Set Adapter on RecyclerView
        recyclerViewCategory.adapter = recyclerViewAdapterCategory
    }
}