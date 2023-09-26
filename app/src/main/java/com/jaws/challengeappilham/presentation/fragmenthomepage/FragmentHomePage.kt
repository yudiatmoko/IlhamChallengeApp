package com.jaws.challengeappilham.presentation.fragmenthomepage

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
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
import com.jaws.challengeappilham.presentation.activitydetail.ActivityMenuDetail

class FragmentHomePage : Fragment() {

    private lateinit var binding: FragmentHomePageBinding

    private val dataSource : MenuDataSource by lazy { MenuDataSourceImpl() }

    private val adapterMenu: MenuListAdapter by lazy {
        MenuListAdapter(AdapterLayoutMode.LINEAR){
            menu: Menu -> navigateToDetail(menu)
        }
    }

    private fun navigateToDetail(menu: Menu? = null) {
//        val action = FragmentHomePageDirections.actionFragmentHomePageToFragmentMenuDetail(menu)
//        findNavController().navigate(action)
        val intent = Intent(requireContext(),ActivityMenuDetail::class.java)
        intent.putExtra("menu", menu)
        startActivity(intent)
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
        setButtonMode()
    }

    private fun setRecyclerViewMenu() {
        if(adapterMenu.adapterLayoutMode == AdapterLayoutMode.LINEAR){
            val span = 1
            binding.rvMenu.apply {
                layoutManager = GridLayoutManager(requireContext(),span)
                adapter = this@FragmentHomePage.adapterMenu
            }
        }else{
            val span = 2
            binding.rvMenu.apply {
                layoutManager = GridLayoutManager(requireContext(),span)
                adapter = this@FragmentHomePage.adapterMenu
                binding.ibList.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.ic_list
                    )
                )
            }
        }
        adapterMenu.setData(dataSource.getMenuData())
    }

    private fun setLayoutMode(){
        if (adapterMenu.adapterLayoutMode == AdapterLayoutMode.LINEAR){
            binding.ibList.setImageDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.ic_list
                )
            )
            (binding.rvMenu.layoutManager as GridLayoutManager).spanCount = 2
            adapterMenu.adapterLayoutMode = AdapterLayoutMode.GRID
            adapterMenu.refreshList()
        }else{
            binding.ibList.setImageDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.ic_grid
                )
            )
            (binding.rvMenu.layoutManager as GridLayoutManager).spanCount = 1
            adapterMenu.adapterLayoutMode = AdapterLayoutMode.LINEAR
            adapterMenu.refreshList()
        }
    }

    private fun setButtonMode() {
        binding.ibList.setOnClickListener {
            setLayoutMode()
        }
    }

    private fun setRecyclerViewCategory() {

        val categoryList = mutableListOf<Category>(
            Category(R.string.nasi, R.drawable.img_rice),
            Category(R.string.mie, R.drawable.img_noodle),
            Category(R.string.snack, R.drawable.img_snack),
            Category(R.string.minuman, R.drawable.img_drink)
        )

        val adapterCategory: CategoryListAdapter by lazy {
            CategoryListAdapter(categoryList)
        }
        val layoutManagerCategory = FlexboxLayoutManager(requireContext())
        layoutManagerCategory.flexDirection = FlexDirection.ROW
        layoutManagerCategory.justifyContent = JustifyContent.SPACE_BETWEEN

        binding.rvCategory.adapter = adapterCategory
        binding.rvCategory.layoutManager = layoutManagerCategory
    }
}