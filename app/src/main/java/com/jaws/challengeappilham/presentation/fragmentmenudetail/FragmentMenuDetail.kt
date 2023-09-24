package com.jaws.challengeappilham.presentation.fragmentmenudetail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.jaws.challengeappilham.R
import com.jaws.challengeappilham.databinding.FragmentMenuDetailBinding
import com.jaws.challengeappilham.model.Menu

class FragmentMenuDetail : Fragment() {

    private lateinit var binding: FragmentMenuDetailBinding

    private val menu: Menu? by lazy{
        FragmentMenuDetailArgs.fromBundle(arguments as Bundle).menu
    }

    var count: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMenuDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showMenuData()
        countingClickListener()
        mapsClickListener()
        popBackStack()
    }

    private fun popBackStack() {
        binding.ibArrowBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun countingClickListener() {
        binding.icAdd.setOnClickListener{
            incrementCount()
        }
        binding.icRemove.setOnClickListener{
            decrementCount()
        }
    }

    private fun incrementCount() {
        count++
        binding.tvAmount.text = count.toString()
        val total = menu?.menuPrice?.toInt()!! * count
        binding.btnAddToCart.text = getString(R.string.add_to_cart,total)
    }

    private fun decrementCount() {
        count--
        if(count <= 1){
            count = 1;
        }
        binding.tvAmount.text = (count.toString())
        val total = menu?.menuPrice?.toInt()!! * count
        binding.btnAddToCart.text = getString(R.string.add_to_cart,total)
    }

    private fun showMenuData() {
        if(menu != null){
            binding.ivImgMenuItemDetail.setImageResource(menu?.menuImg!!)
            binding.tvMenuName.text = menu?.menuName
            binding.tvMenuPrice.text = getString(R.string.rupiah, menu?.menuPrice?.toInt())
            binding.tvMenuDesc.text = menu?.menuDesc
            binding.tvLocationDetail.text = getString(R.string.location)
            binding.btnAddToCart.text = getString(R.string.add_to_cart, menu?.menuPrice?.toInt())
        } else{
            Toast.makeText(requireContext(), "Menu is null", Toast.LENGTH_SHORT).show()
        }
    }

    private fun mapsClickListener() {
        binding.cvLocation.setOnClickListener {
            navigateToGoogleMaps()
        }
    }

    private fun navigateToGoogleMaps() {
        val mapsIntentUri = Uri.parse("https://maps.app.goo.gl/QLChXJcYJUuQWPQh8")
        val mapsIntent = Intent(Intent.ACTION_VIEW, mapsIntentUri)
        startActivity(mapsIntent)
    }
}