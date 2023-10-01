package com.example.githubuser.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import coil.load
import com.example.githubuser.R
import com.example.githubuser.adapter.DetailSectionAdapter
import com.example.githubuser.data.db.FavUserDb
import com.example.githubuser.data.entity.FavoriteUser
import com.example.githubuser.data.repository.FavUserRepository
import com.example.githubuser.databinding.FragmentDetailUserBinding
import com.example.githubuser.viewmodel.DetailUserViewModel
import com.example.githubuser.viewmodelFactory.DetailUserViewModelFactory
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserFragment : Fragment() {
    private lateinit var binding: FragmentDetailUserBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailUserBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val username = arguments?.getString(EXTRA_USERNAME) ?: ""
        val daoInstance = FavUserDb.getInstance(requireActivity()).favUserDao()
        val detailViewmodel: DetailUserViewModel by viewModels {
            DetailUserViewModelFactory(
                username = requireArguments().getString(EXTRA_USERNAME) ?: "",
                usersRepository = FavUserRepository.getInstance(daoInstance)
            )
        }

        detailViewmodel.getDetailuser(username)

        detailViewmodel.isLoading.observe(requireActivity()) { isLoading ->
            showLoading(isLoading)
        }

        val sectionPagerAdapter = DetailSectionAdapter(requireActivity(), username)


        with(binding) {
            detailViewmodel.detailUser.observe(requireActivity()) { user ->
                profileImage.load(user.avatarUrl)
                tvFullName.text = user.name
                tvUsername.text = user.login
                tvFollowing.text = getString(R.string.following_count, user.following)
                tvFollowers.text = getString(R.string.followers_count, user.followers)
                tvBio.text = user.bio
            }
            detailViewmodel.isFavorite.observe(requireActivity()) { isFavorite ->
                if (!isFavorite) {
                    val drawable = ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.ic_favorite
                    )
                    fab.setImageDrawable(drawable)
                    fab.setOnClickListener {
                        FavoriteUser(
                            id = arguments?.getInt(EXTRA_ID) ?: 0,
                            username = username,
                            avatarUrl = arguments?.getString(EXTRA_IMAGE_URL)
                        )
                    }
                } else {
                    val drawable = ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.ic_isfavorit
                    )
                    fab.setImageDrawable(drawable)
                    fab.setOnClickListener {
                        detailViewmodel.deleteFav(username)
                    }
                }
                detailViewmodel.isFav()
            }
            viewPager.adapter = sectionPagerAdapter
            TabLayoutMediator(tabs, viewPager) { tab, position ->
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

    companion object {
        const val EXTRA_ID = "extra_id"
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_IMAGE_URL = "extra_image_url"
    }
}