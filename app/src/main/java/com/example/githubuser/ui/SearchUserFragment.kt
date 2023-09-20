package com.example.githubuser.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubuser.R
import com.example.githubuser.adapter.SearchUserAdapter
import com.example.githubuser.databinding.FragmentSearchUserBinding

class SearchUserFragment : Fragment() {
    private lateinit var searchRv: RecyclerView
    private lateinit var searchUserAdapter: SearchUserAdapter
    private lateinit var binding: FragmentSearchUserBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchUserBinding.inflate(inflater,container,false)
        searchRv = binding.rvUser
        searchRv.layoutManager = LinearLayoutManager(context)
        searchUserAdapter = SearchUserAdapter(requireContext(), emptyList())
        searchRv.adapter = searchUserAdapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}