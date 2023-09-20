package com.example.githubuser.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubuser.R
import com.example.githubuser.adapter.SearchUserAdapter
import com.example.githubuser.databinding.FragmentSearchUserBinding
import com.example.githubuser.viewmodel.SearchUserViewModel

class SearchUserFragment : Fragment() {
    private lateinit var searchRv: RecyclerView
    private lateinit var searchUserAdapter: SearchUserAdapter
    private lateinit var binding: FragmentSearchUserBinding
    private val searchViewModel by viewModels<SearchUserViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchUserBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchRv = binding.rvUser
        searchRv.layoutManager = LinearLayoutManager(context)


        val searchUserViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(SearchUserViewModel::class.java)

        searchUserViewModel.isLoading.observe(requireActivity()) {isLoading ->
            showLoading(isLoading)
        }
        searchUserViewModel.listUser.observe(requireActivity()) {listUser ->
            binding.rvUser.adapter =
                SearchUserAdapter(requireActivity(),listUser)
        }
    }
    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}