package com.example.githubuser.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubuser.R
import com.example.githubuser.adapter.FavUserAdapter
import com.example.githubuser.data.entity.FavoriteUser
import com.example.githubuser.data.response.ItemsItem
import com.example.githubuser.databinding.FragmentFavoriteUserBinding
import com.example.githubuser.viewmodel.DetailUserViewModel
import com.example.githubuser.viewmodel.FavUserViewModel
import com.example.githubuser.viewmodelFactory.FavUserViewModelFactory


class FavoriteUserFragment : Fragment() {
    private lateinit var binding: FragmentFavoriteUserBinding
    private lateinit var favUserRv: RecyclerView
    private lateinit var arrayList: ArrayList<FavoriteUser>
    private lateinit var adapter: FavUserAdapter
    lateinit var favUserViewModel: FavUserViewModel
    lateinit var factory: FavUserViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoriteUserBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arrayList = ArrayList()
        factory =
            FavUserViewModelFactory.getInstance(requireActivity())
        favUserViewModel = ViewModelProvider(this,factory)[FavUserViewModel::class.java]
        favUserRv = binding.rvFollowers
        favUserRv.layoutManager = LinearLayoutManager(context)
        favUserViewModel.favListUser.observe(requireActivity()) { favUsers ->
            showLoading(true)
            if (!favUsers.isNullOrEmpty()){
                showLoading(false)
                binding.favoriteEmpty.visibility = View.GONE
                binding.rvFollowers.visibility = View.VISIBLE
                adapter = FavUserAdapter(favUsers, onCLick = ::onItemClick, onDelete = ::onItemDelete)
                favUserRv.adapter = adapter
            }else{
                showLoading(false)
                binding.favoriteEmpty.visibility = View.VISIBLE
                binding.rvFollowers.visibility = View.GONE
            }

        }

    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun onItemClick(user: FavoriteUser) {
        val bundle = Bundle()
        bundle.putString(DetailUserFragment.EXTRA_USERNAME, user.username)
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

    private fun onItemDelete(username: String) {
        favUserViewModel.deleteFav(username)
        Toast.makeText(requireActivity(), "Berhasil Menghapus", Toast.LENGTH_SHORT).show()
    }
}