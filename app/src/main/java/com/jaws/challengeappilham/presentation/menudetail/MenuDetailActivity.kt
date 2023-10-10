package com.jaws.challengeappilham.presentation.menudetail

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.jaws.challengeappilham.R
import com.jaws.challengeappilham.data.local.database.AppDatabase
import com.jaws.challengeappilham.data.local.database.datasource.CartDataSource
import com.jaws.challengeappilham.data.local.database.datasource.CartDatabaseDataSource
import com.jaws.challengeappilham.data.repository.CartRepository
import com.jaws.challengeappilham.data.repository.CartRepositoryImpl
import com.jaws.challengeappilham.databinding.ActivityMenuDetailBinding
import com.jaws.challengeappilham.model.Menu
import com.jaws.challengeappilham.utils.GenericViewModelFactory
import com.jaws.challengeappilham.utils.proceedWhen

class MenuDetailActivity : AppCompatActivity() {

    private val binding: ActivityMenuDetailBinding by lazy {
        ActivityMenuDetailBinding.inflate(
            layoutInflater
        )
    }

    private val viewModel: MenuDetailViewModel by viewModels {
        val database = AppDatabase.getInstance(this)
        val cartDao = database.cartDao()
        val cartDataSource: CartDataSource = CartDatabaseDataSource(cartDao)
        val repo: CartRepository = CartRepositoryImpl(cartDataSource)
        GenericViewModelFactory.create(MenuDetailViewModel(intent?.extras, repo)
        )
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

        viewModel.addToCartResult.observe(this) {
            it.proceedWhen(
                doOnSuccess = {
                    Toast.makeText(this, "Add to cart success !", Toast.LENGTH_SHORT).show()
                    finish()
                }, doOnError = {
                    Toast.makeText(this, it.exception?.message.orEmpty(), Toast.LENGTH_SHORT).show()
                })
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
        binding.btnAddToCart.setOnClickListener{
            viewModel.addToCart()
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
            val intent = Intent(context, MenuDetailActivity::class.java)
            intent.putExtra(EXTRA_PRODUCT, menu)
            context.startActivity(intent)
        }
    }
}