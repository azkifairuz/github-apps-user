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
import com.example.githubuser.data.response.ItemsItem
import com.example.githubuser.databinding.FragmentSearchUserBinding
import com.example.githubuser.viewmodel.SearchUserViewModel

class SearchUserFragment : Fragment() {
    private lateinit var searchRv: RecyclerView
    private lateinit var binding: FragmentSearchUserBinding
    private lateinit var arrayList: ArrayList<ItemsItem>
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
        arrayList = ArrayList()
        searchRv.adapter = SearchUserAdapter(requireActivity(), arrayList)
        searchRv.layoutManager = LinearLayoutManager(context)

        searchViewModel.isLoading.observe(requireActivity()) { isLoading ->
            showLoading(isLoading)
        }

        searchViewModel.listUser.observe(requireActivity()) { listUser ->
            arrayList.clear()
            arrayList.addAll(listUser)
            searchRv.visibility = View.VISIBLE
            searchRv.adapter?.notifyDataSetChanged()
        }

        with(binding){
            svUser.setupWithSearchBar(sbUser)
            svUser
                .editText
                .setOnEditorActionListener { _, _, _ ->
                    sbUser.text = svUser.text
                    svUser.hide()
                    searchViewModel.searchUser(svUser.text.toString())

                    false
                }
        }
    }
    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}