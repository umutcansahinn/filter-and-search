package com.umutcansahin.filterandsearch

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.umutcansahin.filterandsearch.databinding.AdapterItemBinding

class Adapter : RecyclerView.Adapter<Adapter.ViewHolder>() {
    class ViewHolder(private val binding: AdapterItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun bind(user: User) {
                with(binding) {
                    textViewUserId.text = user.id.toString()
                    textViewUserName.text = user.name
                    textViewUserEmail.text = user.email
                    textViewUserJob.text = user.job.name
                    textViewUserColor.text = user.color.name
                }
            }
        }

    private val diffUtils = object : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }
    }
    private val differ = AsyncListDiffer(this, diffUtils)

    var userList: List<User>
        get() = differ.currentList
        set(value) = differ.submitList(value)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            AdapterItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = userList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(userList[position])
    }
}