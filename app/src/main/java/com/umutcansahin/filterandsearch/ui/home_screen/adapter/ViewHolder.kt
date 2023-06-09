package com.umutcansahin.filterandsearch.ui.home_screen.adapter

import androidx.recyclerview.widget.RecyclerView
import com.umutcansahin.filterandsearch.data.User
import com.umutcansahin.filterandsearch.databinding.AdapterItemBinding

class ViewHolder(private val binding: AdapterItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(user: User) {
        with(binding) {
            textViewUserId.text = user.id.toString()
            textViewUserName.text = user.name
            textViewUserEmail.text = user.email
            textViewUserJob.text = user.jobType.name
            textViewUserColor.text = user.colorType.name
        }
    }
}