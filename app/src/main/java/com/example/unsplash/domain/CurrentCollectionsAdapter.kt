package com.example.unsplash.domain

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import coil.transform.RoundedCornersTransformation
import com.example.unsplash.data.model.PhotosItem
import com.example.unsplash.databinding.ItemCurrentCollectionBinding

class CurrentCollectionsAdapter :
    ListAdapter<PhotosItem, CurrentCollectionsAdapter.CollectViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCurrentCollectionBinding.inflate(inflater, parent, false)
        return CollectViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CollectViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class CollectViewHolder(private val binding: ItemCurrentCollectionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PhotosItem) {
            binding.imageView.load(item.urls.regular) {
                crossfade(true)
                transformations(RoundedCornersTransformation(radius = 20.0f))
            }
            binding.description.text = item.user.name
            binding.author.load(item.user.profile_image.large) {
                crossfade(true)
                transformations(CircleCropTransformation())
            }
        }
    }

    object DiffCallback : DiffUtil.ItemCallback<PhotosItem>() {
        override fun areItemsTheSame(oldItem: PhotosItem, newItem: PhotosItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: PhotosItem,
            newItem: PhotosItem
        ): Boolean {
            return oldItem == newItem
        }
    }
}