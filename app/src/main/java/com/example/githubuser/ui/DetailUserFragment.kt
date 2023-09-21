package com.example.githubuser.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import coil.load
import com.example.githubuser.R
import com.example.githubuser.databinding.FragmentDetailUserBinding

class DetailUserFragment : Fragment() {
    private lateinit var binding: FragmentDetailUserBinding

    companion object {
        const val EXTRA_FULLNAME = "extra_fullname"
        const val EXTRA_IMAGE = "extra_image"
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_FOLOWERS = "extra_followers"
        const val EXTRA_FOLOWINGS = "extra_followings"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailUserBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            profileImage.load(EXTRA_IMAGE)
            tvFullName.text = EXTRA_FULLNAME
            tvUsername.text = EXTRA_USERNAME
            tvFollowers.text = EXTRA_FOLOWERS
            tvFollowing.text = EXTRA_FOLOWINGS
        }
    }

}