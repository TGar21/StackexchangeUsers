package com.example.stackexchange.ui.main

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.stackexchange.data.model.UserDataItem
import com.example.stackexchange.databinding.UserItemBinding

class UsersAdapter:RecyclerView.Adapter<UsersAdapter.ViewHolder>() {

    private lateinit var binding: UserItemBinding
    private var users = mutableListOf<UserDataItem>()

    @SuppressLint("NotifyDataSetChanged")
    fun insertData(inserted: List<UserDataItem>) {
        users.clear()
        users.addAll(inserted)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: UserItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun setData(model: UserDataItem) {
            with(binding) {
                displayName.text = model.display_name
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = UserItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(users[position])
    }

    override fun getItemCount(): Int {
        return users.size
    }
}