package com.jaws.challengeappilham.presentation.fragmenthomepage.menu

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.jaws.challengeappilham.core.ViewHolderBinder
import com.jaws.challengeappilham.databinding.MenuGridItemBinding
import com.jaws.challengeappilham.databinding.MenuListItemBinding
import com.jaws.challengeappilham.model.Menu

class MenuItemGridViewHolder(
    private val binding: MenuGridItemBinding,
    private val onItemClick: (Menu) -> Unit
) : RecyclerView.ViewHolder(binding.root),ViewHolderBinder<Menu> {
    override fun bind(item: Menu) {
        binding.root.setOnClickListener {
            onItemClick.invoke(item)
        }
        binding.ivMenuImage.load(item.menuImg)
        binding.tvMenuName.text = item.menuName
        binding.tvMenuPrice.text = String.format("Rp. %,.0f", item.menuPrice)
    }
}

class MenuItemLinearViewHolder(
    private val binding : MenuListItemBinding,
    private val onItemClick : (Menu) -> Unit
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<Menu> {
    override fun bind(item: Menu) {
        binding.root.setOnClickListener {
            onItemClick.invoke(item)
        }
        binding.ivMenuImg.load(item.menuImg)
        binding.tvMenuName.text = item.menuName
        binding.tvMenuPrice.text = String.format("Rp. %,.0f", item.menuPrice)
    }
}