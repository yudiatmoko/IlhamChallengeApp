package com.jaws.challengeappilham.presentation.cart

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.jaws.challengeappilham.R
import com.jaws.challengeappilham.core.ViewHolderBinder
import com.jaws.challengeappilham.databinding.CartListItemBinding
import com.jaws.challengeappilham.databinding.CheckoutListItemBinding
import com.jaws.challengeappilham.model.Cart
import com.jaws.challengeappilham.utils.doneEditing

class CartItemViewHolder(
    private val binding: CartListItemBinding,
    private val cartListener: CartListener?,
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<Cart> {

    override fun bind(item: Cart) {
        setCartData(item)
        setCartNotes(item)
        setClickListener(item)
    }

    private fun setClickListener(item: Cart) {
        binding.apply {
            icAdd.setOnClickListener { cartListener?.onPlusTotalItemCartClicked(item) }
            icRemove.setOnClickListener { cartListener?.onMinusTotalItemCartClicked(item) }
            icDelete.setOnClickListener { cartListener?.onRemoveCartClicked(item) }
            itemView.setOnClickListener { cartListener?.onCartClicked(item) }
        }
    }

    private fun setCartNotes(item: Cart) {
        binding.etNotes.setText(item.itemNotes)
        binding.etNotes.doneEditing {
            binding.etNotes.clearFocus()
            val newItem = item.copy().apply {
                itemNotes = binding.etNotes.text.toString().trim()
            }
            cartListener?.onUserDoneEditingNotes(newItem)
        }
    }

    private fun setCartData(item: Cart) {
        binding.apply {
            ivMenuImg.load(item.menuImgUrl){crossfade(true)}
            tvMenuName.text = item.menuName
            tvMenuPrice.text = String.format("Rp. %,.0f", (item.menuPrice * item.itemQuantity))
            tvAmount.text = item.itemQuantity.toString()
        }
    }

}

class CheckoutViewHolder(
    private val binding: CheckoutListItemBinding,
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<Cart> {
    override fun bind(item: Cart) {
        setCartData(item)
        setCartNotes(item)
    }

    private fun setCartData(item: Cart) {
        with(binding) {
            binding.ivMenuImg.load(item.menuImgUrl) {
                crossfade(true)
            }
            tvTotalItem.text =
                itemView.rootView.context.getString(
                    R.string.total_qty,
                    item.itemQuantity.toString()
                )
            tvMenuName.text = item.menuName
            tvMenuPrice.text = String.format("Rp. %,.0f", (item.menuPrice * item.itemQuantity))
        }
    }

    private fun setCartNotes(item: Cart) {
        binding.tvNotes.text = item.itemNotes
    }
}

interface CartListener{
    fun onCartClicked(item: Cart)
    fun onPlusTotalItemCartClicked(cart: Cart)
    fun onMinusTotalItemCartClicked(cart: Cart)
    fun onRemoveCartClicked(cart: Cart)
    fun onUserDoneEditingNotes(cart: Cart)
}