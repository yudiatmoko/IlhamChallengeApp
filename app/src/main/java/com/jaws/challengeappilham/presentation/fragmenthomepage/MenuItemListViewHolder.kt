package com.jaws.challengeappilham.presentation.fragmenthomepage

import androidx.recyclerview.widget.RecyclerView
import com.jaws.challengeappilham.databinding.MenuItemBinding
import com.jaws.challengeappilham.model.Menu


class MenuItemListViewHolder(
    private val binding: MenuItemBinding,
    private val onItemClick: (Menu) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    companion object{
        const val RP = "Rp."
    }

    fun bind(item: Menu) {
        binding.root.setOnClickListener {
            onItemClick.invoke(item)
        }
        binding.ivMenuImage.setImageResource(item.menuImg)
        binding.tvMenuName.text = item.menuName
        binding.tvMenuPrice.text = "$RP ${item.menuPrice.toInt()}"
    }
}