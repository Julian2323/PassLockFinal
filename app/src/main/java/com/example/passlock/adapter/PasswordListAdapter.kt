package com.example.passlock.adapter

import android.view.ViewGroup
import android.view.LayoutInflater
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.passlock.databinding.ListItemPasswordBinding
import com.example.passlock.model.Password


class PasswordListAdapter(private val onItemClicked: (Password) -> Unit):
    ListAdapter<Password, PasswordListAdapter.ViewHolder>(DiffCallback) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ListItemPasswordBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )
    }


    override fun onBindViewHolder(holder: PasswordListAdapter.ViewHolder, position: Int) {

        val current = getItem(position)
        holder.itemView.setOnClickListener {
            onItemClicked(current)
        }
        holder.bind(current)

    }


    class ViewHolder(private var binding: ListItemPasswordBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(password: Password) {
            binding.apply {
                dashboardNameLabel.text = password.passwordName
                dashboardPasswordLabel.text = password.passwordActual

            }
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Password>() {
            override fun areItemsTheSame(oldItem: Password, newItem: Password): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Password, newItem: Password): Boolean {
                return oldItem.passwordName == newItem.passwordName
            }
        }
    }
}