package com.umutcansahin.filterandsearch.ui.home_screen.adapter

import androidx.recyclerview.widget.DiffUtil
import com.umutcansahin.filterandsearch.data.User

class DiffUtilCallBack: DiffUtil.ItemCallback<User>() {
    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem == newItem
    }

}