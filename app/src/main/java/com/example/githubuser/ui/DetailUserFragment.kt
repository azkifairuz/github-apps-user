package com.example.githubuser.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import coil.load
import com.example.githubuser.R
import com.example.githubuser.adapter.DetailSectionAdapter
import com.example.githubuser.databinding.FragmentDetailUserBinding
import com.example.githubuser.viewmodel.DetailUserViewModel
import com.google.android.material.tabs.TabLayoutMediator

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
        val username = arguments?.getString(EXTRA_USERNAME) ?: ""
        detailViewmodel.getDetailuser(username)
        val sectionPagerAdapter = DetailSectionAdapter(requireActivity(),username)
        with(binding) {
            detailViewmodel.detailUser.observe(requireActivity()){user->
                profileImage.load(user.avatarUrl)
                tvFullName.text = user.name
                tvUsername.text =  user.login
                tvFollowing.text =  getString(R.string.following_count,user.following)
                tvFollowers.text = getString(R.string.followers_count,user.followers)
                tvBio.text = user.bio
            }
            viewPager.adapter = sectionPagerAdapter
            TabLayoutMediator(tabs,viewPager){tab,position ->
                tab.text = when (position) {
                    0 -> "Follower"
                    1 -> "Following"
                    else -> ""
                }
            }.attach()

        }
    }
    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}