package com.jaws.challengeappilham.presentation.fragmentcart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.jaws.challengeappilham.core.ViewHolderBinder
import com.jaws.challengeappilham.databinding.CartListItemBinding
import com.jaws.challengeappilham.model.CartMenu

class CartListAdapter(
    private val cartListener: CartListener
) : RecyclerView.Adapter<CartItemViewHolder>()
{
    private val dataDiffer = AsyncListDiffer(this, object : DiffUtil.ItemCallback<CartMenu>() {
            override fun areItemsTheSame(
                oldItem: CartMenu,
                newItem: CartMenu
            ): Boolean {
                return oldItem.cart.id == newItem.cart.id && oldItem.menu.id == newItem.menu.id
            }

            override fun areContentsTheSame(
                oldItem: CartMenu,
                newItem: CartMenu
            ): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }
        }
    )

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CartItemViewHolder {
        return CartItemViewHolder(
            CartListItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ), cartListener
        )
    }

    fun submitData(data: List<CartMenu>) {
        dataDiffer.submitList(data)
    }

    override fun getItemCount(): Int = dataDiffer.currentList.size

    override fun onBindViewHolder(
        holder: CartItemViewHolder,
        position: Int,
    ) {
        (holder as ViewHolderBinder<CartMenu>).bind(dataDiffer.currentList[position])
    }
}