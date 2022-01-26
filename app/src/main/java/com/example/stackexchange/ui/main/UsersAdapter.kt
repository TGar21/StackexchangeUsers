package com.example.stackexchange.ui.main

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.stackexchange.data.model.UserData
import com.example.stackexchange.data.model.UserDataItem
import com.example.stackexchange.databinding.UserItemBinding

class UsersAdapter(private val context: Context) :
    RecyclerView.Adapter<UsersAdapter.ViewHolder>() {

    var onItemClick: ((UserDataItem) -> Unit)? = null
    private var users = UserData(emptyList())

    @SuppressLint("NotifyDataSetChanged")
    fun insertData(inserted: UserData) {
        users = inserted
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(users.items[adapterPosition])
            }
        }

        fun bind(item: UserDataItem) {
            UserItemBinding.bind(this.itemView).apply {
                displayName.text = item.display_name
                id.text = item.user_id.toString()
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            UserItemBinding
                .inflate(LayoutInflater.from(context), viewGroup, false)
                .root
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = users.items[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return users.items.size
    }
}