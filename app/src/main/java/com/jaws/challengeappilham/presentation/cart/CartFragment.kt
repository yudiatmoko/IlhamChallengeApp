package com.jaws.challengeappilham.presentation.cart

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.jaws.challengeappilham.R
import com.jaws.challengeappilham.databinding.FragmentCartBinding
import com.jaws.challengeappilham.model.Cart
import com.jaws.challengeappilham.presentation.checkout.CheckoutActivity
import com.jaws.challengeappilham.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.viewModel

class CartFragment : Fragment() {

    private lateinit var binding: FragmentCartBinding

    private val cartListAdapter: CartListAdapter by lazy {
        CartListAdapter(object : CartListener {
            override fun onCartClicked(item: Cart) {

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

    private val viewModel: CartViewModel by viewModel()

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
        binding.btnCheckout.setOnClickListener {
            context?.startActivity(Intent(requireContext(), CheckoutActivity::class.java))
        }
    }

    private fun observeData() {
        viewModel.cartList.observe(
            viewLifecycleOwner
        ) {
            it.proceedWhen(
                doOnSuccess = { result ->
                    binding.btnCheckout.isClickable = true
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
                    binding.btnCheckout.isClickable = false
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
                    binding.rvCart.isVisible =
                        false
                }
            )
        }
    }

    private fun setupCartList() {
        binding.rvCart.adapter = cartListAdapter
    }
}