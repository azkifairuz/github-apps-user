package com.example.githubuser.adapter

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.githubuser.data.entity.FavoriteUser
import com.example.githubuser.data.response.ItemsItem
import com.example.githubuser.databinding.ItemFollowersUserBinding
import com.example.githubuser.ui.DetailUserFragment

class FavUserAdapter(private var listUser: List<FavoriteUser>,
                     private val onCLick:(user: FavoriteUser) -> Unit,
                     private val onDelete:(username: String) -> Unit) :
    RecyclerView.Adapter<FavUserAdapter.ListViewHolder>() {
    class ListViewHolder(itemView: ItemFollowersUserBinding) : RecyclerView.ViewHolder(itemView.root) {
        val imageProfil = itemView.profileImage
        val fullName = itemView.fullName
        val cardView = itemView.cardView
        val btnDelete = itemView.btnDelete
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemFollowersUserBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int = listUser.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {

        holder.imageProfil.load(
            listUser[position].avatarUrl
        )
        holder.fullName.text = listUser[position].username
        holder.cardView.setOnClickListener {
            onCLick(listUser[position])
        }
        holder.btnDelete.setOnClickListener{
            onDelete(listUser[position].username?:"")
        }

    }
}