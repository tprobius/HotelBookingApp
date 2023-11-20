package com.tprobius.hotelbookingapp.utils.recyclerviewadapter

import androidx.recyclerview.widget.DiffUtil

class ListItemDiffCallback : DiffUtil.ItemCallback<ListItem>() {
    override fun areItemsTheSame(oldItem: ListItem, newItem: ListItem): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: ListItem, newItem: ListItem): Boolean =
        oldItem.equals(newItem)
}