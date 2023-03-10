package com.example.unsplash.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.unsplash.MainViewModel
import com.example.unsplash.databinding.FragmentCollectionsBinding
import com.example.unsplash.domain.CollectionsAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CollectionsFragment : Fragment() {

    private lateinit var binding: FragmentCollectionsBinding
    private val viewModel by activityViewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCollectionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter = CollectionsAdapter{
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
        binding.recyclerView.adapter = adapter
        lifecycleScope.launch {
            viewModel.listData.collectLatest {
                adapter.submitData(it)
            }
        }
    }
}