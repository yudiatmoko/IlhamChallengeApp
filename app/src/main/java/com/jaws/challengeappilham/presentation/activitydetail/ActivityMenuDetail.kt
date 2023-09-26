package com.jaws.challengeappilham.presentation.activitydetail

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.jaws.challengeappilham.R
import com.jaws.challengeappilham.databinding.ActivityMenuDetailBinding
import com.jaws.challengeappilham.model.Menu

class ActivityMenuDetail : AppCompatActivity() {

    private val binding: ActivityMenuDetailBinding by lazy {
        ActivityMenuDetailBinding.inflate(
            layoutInflater
        )
    }

    private val menu: Menu? by lazy {
        intent.getParcelableExtra<Menu>("menu")
    }

    private var count: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        count = 0

        showMenuData()
        countingClickListener()
        mapsClickListener()
        backToHomeClickListener()
    }

    private fun backToHomeClickListener() {
        binding.ibArrowBack.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    private fun countingClickListener() {
        binding.icAdd.setOnClickListener {
            incrementCount()
        }
        binding.icRemove.setOnClickListener {
            decrementCount()
        }
    }

    private fun incrementCount() {
        count = (count ?: 0) + 1
        binding.tvAmount.text = count.toString()
        val total = (menu?.menuPrice?.toInt()
            ?: 0) * (count ?: 0)
        binding.btnAddToCart.text =
            getString(R.string.add_to_cart, total)
    }

    private fun decrementCount() {
        count = (count ?: 0) - 1
        if ((count ?: 0) <= 0) return
        binding.tvAmount.text = (count.toString())
        val total = (menu?.menuPrice?.toInt()
            ?: 0) * (count ?: 0)
        binding.btnAddToCart.text =
            getString(R.string.add_to_cart, total)
    }

    private fun showMenuData() {
        if (menu != null) {
            binding.ivImgMenuItemDetail.setImageResource(
                menu?.menuImg!!
            )
            binding.tvMenuName.text =
                menu?.menuName
            binding.tvMenuPrice.text = getString(
                R.string.rupiah,
                menu?.menuPrice?.toInt()
            )
            binding.tvMenuDesc.text =
                menu?.menuDesc
            binding.tvLocationDetail.text =
                getString(R.string.location)
            binding.btnAddToCart.text = getString(
                R.string.add_to_cart,
                menu?.menuPrice?.toInt()
            )
        } else {
            Toast.makeText(
                this,
                "Menu is null",
                Toast.LENGTH_SHORT
            ).show()
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
}