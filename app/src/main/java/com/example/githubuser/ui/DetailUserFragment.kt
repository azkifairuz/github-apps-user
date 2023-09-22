package com.example.githubuser.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import coil.load
import com.example.githubuser.R
import com.example.githubuser.databinding.FragmentDetailUserBinding
import com.example.githubuser.viewmodel.DetailUserViewModel

class DetailUserFragment : Fragment() {
    private lateinit var binding: FragmentDetailUserBinding
    private val detailViewmodel by viewModels<DetailUserViewModel>()
    companion object {
        const val EXTRA_USERNAME = "extra_username"
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
        detailViewmodel.isLoading.observe(requireActivity()) {isLoading->
            showLoading(isLoading)
        }

        if (arguments != null){
            arguments?.getString(EXTRA_USERNAME)?.let { detailViewmodel.getDetailuser(it) }
        }
        with(binding) {
            detailViewmodel.detailUser.observe(requireActivity()){user->
                profileImage.load(user.avatarUrl)
                tvFullName.text = user.name
                tvUsername.text =  user.login
               tvFollowing.text =  getString(R.string.following_count,user.following)
                tvFollowers.text = getString(R.string.followers_count,user.followers)
                tvBio.text = user.bio
            }
        }
    }
    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}