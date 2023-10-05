package com.jaws.challengeappilham.presentation.fragmenthomepage.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.jaws.challengeappilham.core.ViewHolderBinder
import com.jaws.challengeappilham.databinding.CategoryItemBinding
import com.jaws.challengeappilham.model.Category

class CategoryListAdapter : RecyclerView.Adapter<ViewHolder>(){

    private val differ = AsyncListDiffer(this,
        object : DiffUtil.ItemCallback<Category>() {
            override fun areItemsTheSame(
                oldItem: Category,
                newItem: Category,
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: Category,
                newItem: Category,
            ): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }
        })

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolder {
        return CategoryItemViewHolder(
            binding = CategoryItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int,
    ) {
        (holder as ViewHolderBinder<Category>).bind(differ.currentList[position])
    }

    fun setData(data: List<Category>) {
        differ.submitList(data)
    }

    fun refreshList() {
        notifyItemRangeChanged(0,differ.currentList.size)
    }

}