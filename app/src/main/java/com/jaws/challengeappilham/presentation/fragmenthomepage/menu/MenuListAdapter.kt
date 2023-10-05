package com.jaws.challengeappilham.presentation.fragmenthomepage.menu

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.jaws.challengeappilham.core.ViewHolderBinder
import com.jaws.challengeappilham.databinding.MenuGridItemBinding
import com.jaws.challengeappilham.databinding.MenuListItemBinding
import com.jaws.challengeappilham.model.Menu
import com.jaws.challengeappilham.presentation.fragmenthomepage.AdapterLayoutMode

class MenuListAdapter(
    var adapterLayoutMode: AdapterLayoutMode,
    private val onItemClick: (Menu) -> Unit,
) : RecyclerView.Adapter<ViewHolder>() {

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

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolder {
        return when (viewType){
            AdapterLayoutMode.GRID.ordinal -> {
                MenuItemGridViewHolder(
                    binding = MenuGridItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ),
                    onItemClick
                )
            }
            else -> {
                MenuItemLinearViewHolder(
                    binding = MenuListItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ),
                    onItemClick
                )
            }
        }
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int,
    ) {
        (holder as ViewHolderBinder<Menu>).bind(differ.currentList[position])
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun getItemViewType(position: Int): Int {
        return adapterLayoutMode.ordinal
    }

    fun setData(data: List<Menu>) {
        differ.submitList(data)
    }

    fun refreshList() {
        notifyItemRangeChanged(0,differ.currentList.size)
    }
}