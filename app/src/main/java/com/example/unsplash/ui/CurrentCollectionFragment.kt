package com.example.unsplash.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.unsplash.databinding.FragmentCurrentCollectionBinding

class CurrentCollectionFragment : Fragment() {

    private lateinit var binding: FragmentCurrentCollectionBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCurrentCollectionBinding.inflate(inflater, container, false)
        return binding.root
    }
}