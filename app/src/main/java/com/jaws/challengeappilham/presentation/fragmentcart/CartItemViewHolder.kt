package com.jaws.challengeappilham.presentation.fragmentcart

import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.jaws.challengeappilham.core.ViewHolderBinder
import com.jaws.challengeappilham.databinding.CartListItemBinding
import com.jaws.challengeappilham.model.Cart
import com.jaws.challengeappilham.model.CartMenu

class CartItemViewHolder(
    private val binding: CartListItemBinding,
    private val cartListener: CartListener,
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<CartMenu> {

    override fun bind(item: CartMenu) {
        setCartData(item)
        setCartNotes(item)
        setClickListener(item)
    }

    private fun setClickListener(item: CartMenu) {
        binding.apply {
            icAdd.setOnClickListener { cartListener.onMinusTotalItemCartClicked(item.cart) }
            icDelete.setOnClickListener { cartListener.onPlusTotalItemCartClicked(item.cart) }
            icRemove.setOnClickListener { cartListener.onRemoveCartClicked(item.cart) }
            itemView.setOnClickListener { cartListener.onCartClicked(item) }
        }
    }

    private fun setCartNotes(item: CartMenu) {
        binding.etNotes.setText(item.cart.itemNotes)
        binding.etNotes.setOnEditorActionListener{_, action, event ->
            if (action == EditorInfo.IME_ACTION_SEARCH ||
                action == EditorInfo.IME_ACTION_DONE ||
                event != null &&
                event.action == KeyEvent.ACTION_DOWN &&
                event.keyCode == KeyEvent.KEYCODE_ENTER){
                if (event == null || !event.isShiftPressed) {
                    // the user is done typing.
                    val newItem = item.cart.apply {
                        itemNotes = binding.etNotes.text.toString().trim()
                    }
                    cartListener.onUserDoneEditingNotes(newItem)
                    return@setOnEditorActionListener true
                }
            }
            return@setOnEditorActionListener true
        }
    }

    private fun setCartData(item: CartMenu) {
        binding.apply {
            binding.ivMenuImg.load(item.menu.menuImg){crossfade(true)}
        }
        binding.tvMenuName.text = item.menu.menuName
        binding.tvMenuPrice.text = String.format("Rp. %,.0f", (item.menu.menuPrice * item.cart.itemQuantity))
        binding.tvAmount.text = item.cart.itemQuantity.toString()
    }

}

interface CartListener{
    fun onCartClicked(item: CartMenu)
    fun onPlusTotalItemCartClicked(cart: Cart)
    fun onMinusTotalItemCartClicked(cart: Cart)
    fun onRemoveCartClicked(cart: Cart)
    fun onUserDoneEditingNotes(newCart: Cart)
}