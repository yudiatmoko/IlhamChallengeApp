package com.jaws.mybinarchallengeapp.page.fragmentmenudetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import com.jaws.mybinarchallengeapp.R
import com.jaws.mybinarchallengeapp.databinding.FragmentMenuDetailBinding
import com.jaws.mybinarchallengeapp.model.Menu

class FragmentMenuDetail : Fragment() {

    private lateinit var binding: FragmentMenuDetailBinding

    private val menu: Menu? by lazy{
        FragmentMenuDetailArgs.fromBundle(arguments as Bundle).menu
    }

<<<<<<< HEAD
    companion object{
        const val ADD_TO_CART = "Tambah ke Keranjang - "
        const val RP = "Rp."
    }

=======
>>>>>>> origin/feature/feature_menu_detail
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMenuDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showProfileData()
    }

    private fun showProfileData() {
        if(menu != null){
<<<<<<< HEAD
            binding.ivImgMenuItemDetail.setImageResource(menu?.menuImg!!)
            binding.tvMenuName.text = menu?.menuName
            binding.tvMenuPrice.text = "$RP ${menu?.menuPrice?.toInt()}"
            binding.btnAddToCart.text = "$ADD_TO_CART $RP ${menu?.menuPrice?.toInt()}"
            binding.tvMenuDesc.text = menu?.menuDesc
=======
            menu?.menuImg?.let { binding.ivImgMenuItemDetail.setImageResource(it) }
            binding.tvMenuName.text = menu?.menuName
            binding.tvMenuPrice.text = menu?.menuPrice
            binding.btnAddToCart.text = "Tambah ke Keranjang - ${ menu?.menuPrice.toString() }"
>>>>>>> origin/feature/feature_menu_detail
        } else{
            Toast.makeText(requireContext(), "Profile is null", Toast.LENGTH_SHORT).show()
        }
    }
}