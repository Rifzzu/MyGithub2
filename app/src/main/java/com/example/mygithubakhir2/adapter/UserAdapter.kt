package com.example.mygithubakhir2.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mygithubakhir2.User
import com.example.mygithubakhir2.databinding.ItemRowUserBinding

class UserAdapter : RecyclerView.Adapter<UserAdapter.ListViewHolder>(){

    private var onItemClickCallback: OnItemClickCallback? = null
    private val listUser = ArrayList<User>()

    fun setData(items: ArrayList<User>) {
        listUser.clear()
        listUser.addAll(items)
        notifyDataSetChanged()
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class ListViewHolder(val binding: ItemRowUserBinding): RecyclerView.ViewHolder(binding.root) {
        // fungsi untuk mengisi data ke dalam view
        fun bind(user: User) {
            binding.root.setOnClickListener {
                onItemClickCallback?.onItemClicked(user)
            }

            binding.apply {
                Glide.with(itemView)
                    .load(user.avatar_url)
                    .into(imgAvatar)

                tvAccount.text = user.login

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int = listUser.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listUser[position])
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: User)
    }
}