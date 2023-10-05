package com.example.githubuser.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.R
import com.example.githubuser.adapter.SearchUserAdapter
import com.example.githubuser.data.response.ItemsItem
import com.example.githubuser.databinding.FragmentFollowBinding
import com.example.githubuser.viewmodel.DetailUserViewModel
import com.example.githubuser.viewmodelFactory.DetailUserViewModelFactory

class FollowFragment : Fragment(), SearchUserAdapter.ToDetailCallback {
    private lateinit var binding: FragmentFollowBinding

    companion object {
        const val ARG_POSITION = "index_position"
        const val ARG_USERNAME = "username"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val indexPosition = arguments?.getInt(ARG_POSITION, 0)!!
        val userName = arguments?.getString(ARG_USERNAME) ?: "empty"
        val factory: DetailUserViewModelFactory =
            DetailUserViewModelFactory.getInstance(requireActivity())
        factory.setUsername(userName)
        val detailViewmodel: DetailUserViewModel by viewModels {
            factory
        }
        detailViewmodel.getFollowersList(userName)
        detailViewmodel.getFollowingList(userName)
        with(binding) {
            detailViewmodel.isLoadingFollow.observe(requireActivity()) { isLoading ->
                showLoading(isLoading)
            }
            rvFollow.layoutManager = LinearLayoutManager(requireActivity())
            when (indexPosition) {
                1 -> {
                    detailViewmodel.followerList.observe(requireActivity()) {
                        val adapter = SearchUserAdapter(requireActivity(), it)
                        rvFollow.adapter = adapter
                        adapter.setToDetailCallback(this@FollowFragment)
                    }
                }

                2 -> {
                    detailViewmodel.followingList.observe(requireActivity()) {
                        val adapter = SearchUserAdapter(requireActivity(), it)
                        rvFollow.adapter = adapter
                        adapter.setToDetailCallback(this@FollowFragment)
                    }
                }
            }

        }

    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onItemClicked(user: ItemsItem) {
        val bundle = Bundle()
        bundle.putString(DetailUserFragment.EXTRA_USERNAME, user.login)
        val detailFragment = DetailUserFragment()
        detailFragment.arguments = bundle
        val fragmentManager = parentFragmentManager
        fragmentManager.beginTransaction().apply {
            replace(
                R.id.frame_container,
                detailFragment,
                DetailUserFragment::class.java.simpleName
            )
            addToBackStack(null)
            commit()
        }
    }
}