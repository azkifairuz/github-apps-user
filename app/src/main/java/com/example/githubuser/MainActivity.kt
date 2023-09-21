package com.example.githubuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.githubuser.ui.SearchUserFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragmentManager = supportFragmentManager
        val searchUserFragment = SearchUserFragment()
        val fragment = fragmentManager
            .findFragmentByTag(SearchUserFragment::class.java.simpleName)
        if (fragment !is SearchUserFragment){
            fragmentManager
                .beginTransaction()
                .add(
                    R.id.frame_container,
                    searchUserFragment,
                    SearchUserFragment::class.java.simpleName
                )
                .commit()
        }
    }
}