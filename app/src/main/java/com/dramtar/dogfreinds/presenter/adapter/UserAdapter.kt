package com.dramtar.dogfreinds.presenter.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dramtar.dogfreinds.databinding.UserItemBinding
import com.dramtar.dogfreinds.domain.model.User


class UserAdapter(private val listener: OnItemClickListener) :
    PagingDataAdapter<User, UserAdapter.UserViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = UserItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    inner class UserViewHolder(private val binding: UserItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.apply {
                root.setOnClickListener {
                    val position = absoluteAdapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        getItem(position)?.let { listener.onItemClick(it) }
                    }
                }
            }
        }

        fun bind(user: User) {
            binding.apply {
                Glide.with(itemView)
                    .load(user.pictureMedium)
                    .into(doggyAvatarView)
                itemContainer.setBackgroundColor(user.bgColor)

                nickName.text = user.firstName
                idView.text = user.id.toString()
                emailView.text = user.email

                nickName.setTextColor(user.nameColor)
                idView.setTextColor(user.nameColor)
                emailView.setTextColor(user.nameColor)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(user: User)
    }


    class DiffCallback : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.email == newItem.email
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }
    }
}