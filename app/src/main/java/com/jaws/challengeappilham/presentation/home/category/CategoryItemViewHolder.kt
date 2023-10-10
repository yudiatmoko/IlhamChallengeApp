package com.jaws.challengeappilham.presentation.home.category

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.jaws.challengeappilham.core.ViewHolderBinder
import com.jaws.challengeappilham.databinding.CategoryItemBinding
import com.jaws.challengeappilham.model.Category

class CategoryItemViewHolder(
    private val binding: CategoryItemBinding
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<Category>{

    override fun bind(item: Category) {
        binding.tvCategoryName.text = item.catName
        binding.ivCategoryImage.load(item.catImgSrc)
    }
}