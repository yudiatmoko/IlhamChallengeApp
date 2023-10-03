package com.jaws.challengeappilham.presentation.activitydetail

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.jaws.challengeappilham.R
import com.jaws.challengeappilham.databinding.ActivityMenuDetailBinding
import com.jaws.challengeappilham.model.Menu
import com.jaws.challengeappilham.utils.GenericViewModelFactory

class ActivityMenuDetail : AppCompatActivity() {

    private val binding: ActivityMenuDetailBinding by lazy {
        ActivityMenuDetailBinding.inflate(
            layoutInflater
        )
    }

    private val viewModel: MenuDetailViewModel by viewModels {
        GenericViewModelFactory.create(MenuDetailViewModel(intent?.extras))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        showMenuData(viewModel.menu)
        backToHomeClickListener()
        countingClickListener()
        observeData()
        mapsClickListener()
    }

    private fun observeData() {
        viewModel.priceLiveData.observe(this){
            binding.btnAddToCart.text =  getString(R.string.add_to_cart, it.toInt())
        }

        viewModel.menuCountLiveData.observe(this){
            binding.tvAmount.text = it.toString()
        }
    }

    private fun backToHomeClickListener() {
        binding.ibArrowBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun countingClickListener() {
        binding.icAdd.setOnClickListener {
            viewModel.add()
        }
        binding.icRemove.setOnClickListener {
            viewModel.minus()
        }
    }

    private fun showMenuData(menu: Menu?) {
        menu?.let {
            binding.ivImgMenuItemDetail.load(it.menuImg)
            binding.tvMenuName.text = it.menuName
            binding.tvMenuPrice.text = String.format("Rp. %,.0f", it.menuPrice)
            binding.tvMenuDesc.text = it.menuDesc
            binding.tvLocationDetail.text = getString(R.string.location)
            binding.btnAddToCart.text = getString(R.string.add_to_cart, it.menuPrice.toInt())
        }
    }

    private fun mapsClickListener() {
        binding.cvLocation.setOnClickListener {
            navigateToGoogleMaps()
        }
    }

    private fun navigateToGoogleMaps() {
        val mapsIntentUri =
            Uri.parse("https://maps.app.goo.gl/QLChXJcYJUuQWPQh8")
        val mapsIntent = Intent(
            Intent.ACTION_VIEW,
            mapsIntentUri
        )
        startActivity(mapsIntent)
    }

    companion object {
        const val EXTRA_PRODUCT = "EXTRA_PRODUCT"
        fun startActivity(context: Context, menu: Menu) {
            val intent = Intent(context, ActivityMenuDetail::class.java)
            intent.putExtra(EXTRA_PRODUCT, menu)
            context.startActivity(intent)
        }
    }
}