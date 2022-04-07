package com.dramtar.dogfreinds.presenter.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dramtar.dogfreinds.R
import com.dramtar.dogfreinds.databinding.UserItemBinding
import com.dramtar.dogfreinds.domain.model.User
import com.dramtar.dogfreinds.utils.getColorFromAttr


class UserAdapter(private val listener: OnItemClickListener) :
    PagingDataAdapter<User, UserAdapter.UserViewHolder>(DiffCallback()) {

    private lateinit var context: Context
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
                val defBGColor = context.getColorFromAttr(R.attr.itemBackground)
                val defTextColor = context.getColorFromAttr(com.google.android.material.R.attr.colorOnPrimary)

                Glide.with(itemView)
                    .load(user.pictureMedium)
                    .into(doggyAvatarView)
                itemContainer.setBackgroundColor(if (user.id % 2 == 0) user.bgColor else defBGColor)

                nickName.text = user.firstName
                idView.text = user.id.toString()
                emailView.text = user.email

                nickName.setTextColor(if (user.id % 2 == 0) user.nameColor else defTextColor)
                idView.setTextColor(if (user.id % 2 == 0) user.nameColor else defTextColor)
                emailView.setTextColor(if (user.id % 2 == 0) user.nameColor else defTextColor)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(user: User)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        context = recyclerView.context
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