package com.jaws.challengeappilham.presentation.fragmenthomepage

import androidx.recyclerview.widget.RecyclerView
import com.jaws.challengeappilham.databinding.MenuGridItemBinding
import com.jaws.challengeappilham.model.Menu


class MenuItemGridViewHolder(
    private val binding: MenuGridItemBinding,
    private val onItemClick: (Menu) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Menu) {
        binding.root.setOnClickListener {
            onItemClick.invoke(item)
        }
        binding.ivMenuImage.setImageResource(item.menuImg)
        binding.tvMenuName.text = item.menuName
        binding.tvMenuPrice.text = "Rp. ${item.menuPrice.toInt()}"
    }
}