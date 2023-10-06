package com.jaws.challengeappilham.presentation.fragmentcart

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.jaws.challengeappilham.R
import com.jaws.challengeappilham.data.local.database.AppDatabase
import com.jaws.challengeappilham.data.local.database.datasource.CartDataSource
import com.jaws.challengeappilham.data.local.database.datasource.CartDatabaseDataSource
import com.jaws.challengeappilham.data.repository.CartRepository
import com.jaws.challengeappilham.data.repository.CartRepositoryImpl
import com.jaws.challengeappilham.databinding.FragmentCartBinding
import com.jaws.challengeappilham.model.Cart
import com.jaws.challengeappilham.model.CartMenu
import com.jaws.challengeappilham.utils.GenericViewModelFactory
import com.jaws.challengeappilham.utils.proceedWhen

class FragmentCart : Fragment() {

    private lateinit var binding: FragmentCartBinding

    private val cartListAdapter: CartListAdapter by lazy {
        CartListAdapter(object : CartListener {
            override fun onCartClicked(item: CartMenu) {

            }

            override fun onPlusTotalItemCartClicked(
                cart: Cart,
            ) {
                viewModel.increaseCart(cart)
            }

            override fun onMinusTotalItemCartClicked(
                cart: Cart,
            ) {
                viewModel.decreaseCart(cart)
            }

            override fun onRemoveCartClicked(cart: Cart) {
                viewModel.deleteCart(cart)
            }

            override fun onUserDoneEditingNotes(
                newCart: Cart,
            ) {
                viewModel.updateNotes(newCart)
            }
        })
    }

    private val viewModel: CartViewModel by viewModels {
        val database =
            AppDatabase.getInstance(requireContext())
        val cartDao = database.cartDao()
        val cartDataSource: CartDataSource =
            CartDatabaseDataSource(cartDao)
        val repo: CartRepository =
            CartRepositoryImpl(cartDataSource)
        GenericViewModelFactory.create(
            CartViewModel(repo)
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCartBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(
            view,
            savedInstanceState
        )

        setupCartList()
        observeData()
        setClickListener()
    }

    private fun setClickListener() {

    }

    private fun observeData() {
        viewModel.cartList.observe(
            viewLifecycleOwner
        ) {
            it.proceedWhen(
                doOnSuccess = { result ->
                    binding.layoutState.root.isVisible =
                        false
                    binding.layoutState.pbLoading.isVisible =
                        false
                    binding.layoutState.tvError.isVisible =
                        false
                    binding.rvCart.isVisible =
                        true
                    result.payload?.let { (carts, totalPrice) ->
                        cartListAdapter.submitData(
                            carts
                        )
                        binding.tvTotalPriceAmount.text =
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
                    binding.rvCart.isVisible =
                        false
                },
                doOnEmpty = { result ->
                    binding.layoutState.root.isVisible =
                        true
                    binding.rvCart.isVisible =
                        false
                    binding.layoutState.pbLoading.isVisible =
                        false
                    binding.layoutState.tvError.isVisible =
                        true
                    binding.layoutState.tvError.text =
                        getString(R.string.no_data_text)
                    result.payload?.let { (_, totalPrice) ->
                        binding.tvTotalPriceAmount.text =
                            String.format(
                                "Rp. %,.0f",
                                totalPrice
                            )
                    }
                }
            )
        }
    }

    private fun setupCartList() {
        binding.rvCart.adapter = cartListAdapter
    }
}