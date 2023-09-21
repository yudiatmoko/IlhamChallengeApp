package com.jaws.challengeappilham.presentation.fragmenthomepage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.jaws.challengeappilham.databinding.MenuGridItemBinding
import com.jaws.challengeappilham.model.Menu

class MenuListAdapter(
    private val onItemClick: (Menu) -> Unit,
) : RecyclerView.Adapter<MenuItemGridViewHolder>() {

    private val differ = AsyncListDiffer(this,
        object : DiffUtil.ItemCallback<Menu>() {
            override fun areItemsTheSame(
                oldItem: Menu,
                newItem: Menu,
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: Menu,
                newItem: Menu,
            ): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }
        })

    fun setData(data: List<Menu>) {
        differ.submitList(data)
        notifyItemRangeChanged(0, data.size)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): MenuItemGridViewHolder {
        return MenuItemGridViewHolder(
            binding = MenuGridItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onItemClick = onItemClick
        )
    }

    override fun onBindViewHolder(
        holder: MenuItemGridViewHolder,
        position: Int,
    ) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int = differ.currentList.size
}