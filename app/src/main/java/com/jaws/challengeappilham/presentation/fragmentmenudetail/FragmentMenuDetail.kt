package com.jaws.challengeappilham.presentation.fragmentmenudetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.jaws.challengeappilham.databinding.FragmentMenuDetailBinding
import com.jaws.challengeappilham.model.Menu

class FragmentMenuDetail : Fragment() {

    private lateinit var binding: FragmentMenuDetailBinding

    private val menu: Menu? by lazy{
        FragmentMenuDetailArgs.fromBundle(arguments as Bundle).menu
    }

    companion object{
        const val ADD_TO_CART = "Tambah ke Keranjang - "
        const val RP = "Rp."
    }

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
            binding.ivImgMenuItemDetail.setImageResource(menu?.menuImg!!)
            binding.tvMenuName.text = menu?.menuName
            binding.tvMenuPrice.text = "$RP ${menu?.menuPrice?.toInt()}"
            binding.btnAddToCart.text = "$ADD_TO_CART $RP ${menu?.menuPrice?.toInt()}"
            binding.tvMenuDesc.text = menu?.menuDesc

        } else{
            Toast.makeText(requireContext(), "Profile is null", Toast.LENGTH_SHORT).show()
        }
    }
}