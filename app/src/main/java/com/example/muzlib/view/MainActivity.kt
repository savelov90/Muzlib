package com.example.muzlib.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.muzlib.R
import com.example.muzlib.data.db.entity_search.ResultAlbums
import com.example.muzlib.databinding.ActivityMainBinding
import com.example.muzlib.view.fragments.DetailsFragment
import com.example.muzlib.view.fragments.HomeFragment

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment_placeholder, HomeFragment())
            .addToBackStack(null)
            .commit()
    }

    fun launchDetailsFragment(resultAlbums: ResultAlbums) {
        val bundle = Bundle()
        bundle.putParcelable("album", resultAlbums)
        val fragment = DetailsFragment()
        fragment.arguments = bundle

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_placeholder, fragment)
            .addToBackStack(null)
            .commit()
    }
}