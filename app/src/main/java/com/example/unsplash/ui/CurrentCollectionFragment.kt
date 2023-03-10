package com.example.unsplash.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.unsplash.MainViewModel
import com.example.unsplash.databinding.FragmentCurrentCollectionBinding
import com.example.unsplash.domain.CurrentCollectionsAdapter
import com.example.unsplash.domain.ResultOf
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CurrentCollectionFragment : Fragment() {

    private lateinit var binding: FragmentCurrentCollectionBinding
    private val viewModel by activityViewModels<MainViewModel>()
    private val navArgs by navArgs<CurrentCollectionFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCurrentCollectionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter = CurrentCollectionsAdapter()
        binding.topAppBar.setupWithNavController(
            findNavController(),
            AppBarConfiguration(findNavController().graph)
        )
        binding.recyclerView.adapter = adapter
        lifecycleScope.launch {
            viewModel.currentFlow(navArgs.arg).collectLatest { result ->
                when (result) {
                    is ResultOf.Loading -> {}
                    is ResultOf.Success -> {
                        adapter.submitList(result.data!!)
                    }
                    is ResultOf.Failure -> {}
                }
            }
        }
    }

    companion object {
        fun newFragment(arg: String): Fragment {
            val bundle = Bundle().apply { putString("args", arg) }
            val fragment = CurrentCollectionFragment().apply { arguments = bundle }
            return fragment
        }
    }
}