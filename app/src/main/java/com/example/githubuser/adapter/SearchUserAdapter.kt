package com.example.githubuser.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.githubuser.data.response.ItemsItem
import com.example.githubuser.databinding.ItemUserBinding

class SearchUserAdapter(private val context: Context, private var listUser: List<ItemsItem>) :
    RecyclerView.Adapter<SearchUserAdapter.ListViewHolder>() {
    private lateinit var toDetailCallback: ToDetailCallback


    fun setToDetailCallback(toDetailCallback: ToDetailCallback){
        this.toDetailCallback = toDetailCallback
    }
    class ListViewHolder(itemView: ItemUserBinding) : RecyclerView.ViewHolder(itemView.root) {
        val imageProfil = itemView.profileImage
        val fullName = itemView.fullName
        val linkProfile = itemView.linkUrl
        val cardView = itemView.cardView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemUserBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ListViewHolder(binding)

    }

    override fun getItemCount(): Int = listUser.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.imageProfil.load(
            listUser[position].avatarUrl
        )
        holder.fullName.text = listUser[position].login
        holder.linkProfile.text = listUser[position].htmlUrl
        holder.linkProfile.setOnClickListener {
            context.startActivity(
                Intent
                    (Intent.ACTION_VIEW, Uri.parse(holder.linkProfile.text.toString()))
            )
        }
        holder.cardView.setOnClickListener {
            toDetailCallback.onItemClicked(listUser[position])
        }
    }
    interface ToDetailCallback {
        fun onItemClicked(user:ItemsItem)
    }

}