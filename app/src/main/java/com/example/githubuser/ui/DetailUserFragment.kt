package com.example.githubuser.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.githubuser.R
import com.example.githubuser.databinding.FragmentDetailUserBinding

class DetailUserFragment : Fragment() {
    private lateinit var binding:FragmentDetailUserBinding

    companion object{
        const val EXTRA_FULLNAME = "extra_fullname"
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_FOLOWERS = "extra_followers"
        const val EXTRA_FOLOWINGS = "extra_followings"
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailUserBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

}