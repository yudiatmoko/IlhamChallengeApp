package com.jaws.challengeappilham.presentation.checkout

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.jaws.challengeappilham.R
import com.jaws.challengeappilham.data.local.database.AppDatabase
import com.jaws.challengeappilham.data.local.database.datasource.CartDataSource
import com.jaws.challengeappilham.data.local.database.datasource.CartDatabaseDataSource
import com.jaws.challengeappilham.data.repository.CartRepository
import com.jaws.challengeappilham.data.repository.CartRepositoryImpl
import com.jaws.challengeappilham.databinding.ActivityCheckoutBinding
import com.jaws.challengeappilham.presentation.cart.CartListAdapter
import com.jaws.challengeappilham.utils.GenericViewModelFactory
import com.jaws.challengeappilham.utils.proceedWhen

class CheckoutActivity : AppCompatActivity() {

    private val viewModel: CheckoutViewModel by viewModels {
        val database =
            AppDatabase.getInstance(this)
        val cartDao = database.cartDao()
        val cartDataSource: CartDataSource =
            CartDatabaseDataSource(cartDao)
        val repo: CartRepository =
            CartRepositoryImpl(cartDataSource)
        GenericViewModelFactory.create(
            CheckoutViewModel(repo)
        )
    }

    private val binding: ActivityCheckoutBinding by lazy {
        ActivityCheckoutBinding.inflate(
            layoutInflater
        )
    }

    private val adapter: CartListAdapter by lazy {
        CartListAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupList()
        observeData()
        backToHomeClickListener()
        orderClickListener()
    }

    fun dialogOrder() {
        AlertDialog.Builder(this)
            .setTitle("Pesanan Berhasil")
            .setMessage("Kembali ke Halaman Keranjang?")
            .setPositiveButton("OK") { _, _ -> finish() }
            .show()
    }

    private fun orderClickListener() {
        binding.btnOrder.setOnClickListener {
            dialogOrder()
            viewModel.deleteAll()
        }
    }

    private fun backToHomeClickListener() {
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun setupList() {
        binding.rvCheckout.adapter = adapter
    }

    private fun observeData() {
        viewModel.cartList.observe(this) {
            it.proceedWhen(
                doOnSuccess = { result ->
                    binding.layoutState.root.isVisible =
                        false
                    binding.layoutState.pbLoading.isVisible =
                        false
                    binding.layoutState.tvError.isVisible =
                        false
                    binding.svCheckoutController.isVisible =
                        true
                    result.payload?.let { (carts, totalPrice) ->
                        adapter.submitData(carts)
                        binding.totalPembayaran.text =
                            String.format(
                                "Rp. %,.0f",
                                totalPrice
                            )
                    }
                },
                doOnLoading = {
                    binding.layoutState.root.isVisible =
                        true
                    binding.layoutState.pbLoading.isVisible =
                        true
                    binding.layoutState.tvError.isVisible =
                        false
                    binding.svCheckoutController.isVisible =
                        false
                },
                doOnEmpty = { data ->
                    binding.layoutState.root.isVisible =
                        true
                    binding.layoutState.pbLoading.isVisible =
                        false
                    binding.layoutState.tvError.isVisible =
                        true
                    binding.layoutState.tvError.text =
                        getString(R.string.no_data_text)
                    binding.btnOrder.isClickable =
                        false
                },
                doOnError = { err ->
                    binding.layoutState.root.isVisible =
                        true
                    binding.layoutState.pbLoading.isVisible =
                        false
                    binding.layoutState.tvError.isVisible =
                        true
                    binding.layoutState.tvError.text =
                        err.exception?.message.orEmpty()
                    binding.svCheckoutController.isVisible =
                        false
                    binding.btnOrder.isClickable =
                        false
                }
            )
        }
    }
}