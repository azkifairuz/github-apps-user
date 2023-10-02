package com.example.githubuser.ui

import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.core.content.ContextCompat
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubuser.R
import com.example.githubuser.adapter.FavUserAdapter
import com.example.githubuser.adapter.SearchUserAdapter
import com.example.githubuser.data.response.ItemsItem
import com.example.githubuser.databinding.FragmentSearchUserBinding
import com.example.githubuser.setting.dataStore
import com.example.githubuser.viewmodel.SearchUserViewModel
import com.example.githubuser.viewmodelFactory.SearchViewModelFactory
import com.google.android.material.switchmaterial.SwitchMaterial

class SearchUserFragment : Fragment(), SearchUserAdapter.ToDetailCallback {
    private lateinit var searchRv: RecyclerView
    private lateinit var binding: FragmentSearchUserBinding
    private lateinit var arrayList: ArrayList<ItemsItem>
    private var isDarkModeActiveState = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchUserBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchRv = binding.rvUser
        arrayList = ArrayList()
        val adapter = SearchUserAdapter(requireActivity(), arrayList)
        adapter.setToDetailCallback(this)
        searchRv.adapter = adapter
        searchRv.layoutManager = LinearLayoutManager(context)

        val dataStore = requireContext().dataStore
        val factory: SearchViewModelFactory =
            SearchViewModelFactory.getInstance(requireContext(), dataStore = dataStore )
        val searchViewModel: SearchUserViewModel by viewModels {
            factory
        }

        searchViewModel.isLoading.observe(requireActivity()) { isLoading ->
            showLoading(isLoading)
        }

        searchViewModel.getThemeSettings().observe(requireActivity()) { isDarkModeActive ->
            if (isDarkModeActiveState != isDarkModeActive) {
                isDarkModeActiveState = isDarkModeActive
                if (isDarkModeActive) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

                } else {
                    AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO)
                }
            }
        }

        searchViewModel.listUser.observe(requireActivity()) { listUser ->
            arrayList.clear()
            if (listUser.isEmpty()){
                searchRv.visibility = View.GONE
                binding.userNotFound.visibility = View.VISIBLE
            }else{
                arrayList.addAll(listUser)
                searchRv.visibility = View.VISIBLE
                binding.userNotFound.visibility = View.GONE
                searchRv.adapter?.notifyDataSetChanged()
            }

        }

        with(binding) {

            sbUser.inflateMenu(R.menu.option_menu)
            sbUser.setOnMenuItemClickListener { menuItem ->
                when(menuItem.itemId) {
                    R.id.favPage-> {
                        val favoriteFragment = FavoriteUserFragment()
                        val fragmentManager = parentFragmentManager
                        fragmentManager.beginTransaction().apply {
                            replace(
                                R.id.frame_container,
                                favoriteFragment,
                                DetailUserFragment::class.java.simpleName
                            )
                            addToBackStack(null)
                            commit()
                        }
                    }
                    R.id.changeTheme -> {
                        searchViewModel.saveThemeSetting(!isDarkModeActiveState)
                        if (isDarkModeActiveState){
                            menuItem.setIcon(R.drawable.ic_light_mode)
                        }else{
                            menuItem.setIcon(R.drawable.ic_dark_mode)
                        }
                        true
                    }
                }
                true
            }

            svUser.setupWithSearchBar(sbUser)
            svUser
                .editText
                .setOnEditorActionListener { _, _, _ ->
                    sbUser.text = svUser.text
                    svUser.hide()
                    searchViewModel.searchUser(svUser.text.toString())

                    true
                }
        }
    }

    override fun onItemClicked(user: ItemsItem) {
        val bundle = Bundle()
        bundle.putString(DetailUserFragment.EXTRA_USERNAME, user.login)
        bundle.putString(DetailUserFragment.EXTRA_IMAGE_URL, user.avatarUrl)
        bundle.putInt(DetailUserFragment.EXTRA_ID, user.id)
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

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }



}
