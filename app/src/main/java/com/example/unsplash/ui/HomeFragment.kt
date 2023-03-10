package com.example.unsplash.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.unsplash.MainViewModel
import com.example.unsplash.databinding.FragmentHomeBinding
import com.example.unsplash.domain.PhotosAdapter
import com.example.unsplash.domain.ResultOf
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel by activityViewModels<MainViewModel>()
    private val adapter = PhotosAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.recyclerView.adapter = adapter
        lifecycleScope.launch {
            viewModel.state.collectLatest { result ->
                when (result) {
                    is ResultOf.Failure -> {}
                    is ResultOf.Success -> {
                        adapter.submitList(result.data)
                    }
                    is ResultOf.Loading -> {}
                }
            }
        }
    }
}