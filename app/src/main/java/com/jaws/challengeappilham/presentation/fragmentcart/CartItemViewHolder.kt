package com.jaws.challengeappilham.presentation.fragmentcart

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.jaws.challengeappilham.R
import com.jaws.challengeappilham.core.ViewHolderBinder
import com.jaws.challengeappilham.databinding.CartListItemBinding
import com.jaws.challengeappilham.databinding.CheckoutListItemBinding
import com.jaws.challengeappilham.model.Cart
import com.jaws.challengeappilham.model.CartMenu
import com.jaws.challengeappilham.utils.doneEditing

class CartItemViewHolder(
    private val binding: CartListItemBinding,
    private val cartListener: CartListener?,
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<CartMenu> {

    override fun bind(item: CartMenu) {
        setCartData(item)
        setCartNotes(item)
        setClickListener(item)
    }

    private fun setClickListener(item: CartMenu) {
        binding.apply {
            icAdd.setOnClickListener { cartListener?.onPlusTotalItemCartClicked(item.cart) }
            icRemove.setOnClickListener { cartListener?.onMinusTotalItemCartClicked(item.cart) }
            icDelete.setOnClickListener { cartListener?.onRemoveCartClicked(item.cart) }
            itemView.setOnClickListener { cartListener?.onCartClicked(item) }
        }
    }

    private fun setCartNotes(item: CartMenu) {
        binding.etNotes.setText(item.cart.itemNotes)
        binding.etNotes.doneEditing {
            binding.etNotes.clearFocus()
            val newItem = item.cart.copy().apply {
                itemNotes = binding.etNotes.text.toString().trim()
            }
            cartListener?.onUserDoneEditingNotes(newItem)
        }
    }

    private fun setCartData(item: CartMenu) {
        binding.apply {
            ivMenuImg.load(item.menu.menuImg){crossfade(true)}
            tvMenuName.text = item.menu.menuName
            tvMenuPrice.text = String.format("Rp. %,.0f", (item.menu.menuPrice * item.cart.itemQuantity))
            tvAmount.text = item.cart.itemQuantity.toString()
        }
    }

}

class CheckoutViewHolder(
    private val binding: CheckoutListItemBinding,
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<CartMenu> {
    override fun bind(item: CartMenu) {
        setCartData(item)
        setCartNotes(item)
    }

    private fun setCartData(item: CartMenu) {
        with(binding) {
            binding.ivMenuImg.load(item.menu.menuImg) {
                crossfade(true)
            }
            tvTotalItem.text =
                itemView.rootView.context.getString(
                    R.string.total_qty,
                    item.cart.itemQuantity.toString()
                )
            tvMenuName.text = item.menu.menuName
            tvMenuPrice.text = String.format("Rp. %,.0f", (item.menu.menuPrice * item.cart.itemQuantity))
        }
    }

    private fun setCartNotes(item: CartMenu) {
        binding.tvNotes.text = item.cart.itemNotes
    }
}

interface CartListener{
    fun onCartClicked(item: CartMenu)
    fun onPlusTotalItemCartClicked(cart: Cart)
    fun onMinusTotalItemCartClicked(cart: Cart)
    fun onRemoveCartClicked(cart: Cart)
    fun onUserDoneEditingNotes(cart: Cart)
}