package com.example.unsplash

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.unsplash.databinding.ActivityMainBinding
import com.example.unsplash.ui.StateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }

        val adapter = StateAdapter(supportFragmentManager, lifecycle)

        binding.pager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->
            when (position) {
                0 -> tab.text = "Home"
                1->tab.text = "Collection"
            }
        }.attach()

    }
}
