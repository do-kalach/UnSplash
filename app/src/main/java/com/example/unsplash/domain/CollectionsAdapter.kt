package com.example.unsplash.domain

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import coil.transform.RoundedCornersTransformation
import com.example.unsplash.data.model.CollectionsItem
import com.example.unsplash.databinding.ItemCollectionsBinding

typealias OnClickListener = (String) -> Unit

class CollectionsAdapter(private val onClickListener: OnClickListener) :
    PagingDataAdapter<CollectionsItem, CollectionsAdapter.CollectViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCollectionsBinding.inflate(inflater, parent, false)
        return CollectViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CollectViewHolder, position: Int) {
        val item = getItem(position)!!
        holder.bind(item)
    }

    inner class CollectViewHolder(private val binding: ItemCollectionsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CollectionsItem) {
            binding.root.setOnClickListener {
                onClickListener.invoke(item.links.photos)
            }
            binding.nameCollection.text = item.user.name
            binding.sumPhotos.text = item.total_photos.toString()
            binding.descriptionCollection.text = item.title
            binding.author.load(item.user.profile_image.medium) {
                crossfade(true)
                transformations(CircleCropTransformation())
            }
            binding.imageView.load(item.cover_photo.urls.regular) {
                crossfade(true)
                transformations(RoundedCornersTransformation(radius = 20.0f))
            }
        }
    }


    object DiffCallback : DiffUtil.ItemCallback<CollectionsItem>() {
        override fun areItemsTheSame(oldItem: CollectionsItem, newItem: CollectionsItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: CollectionsItem,
            newItem: CollectionsItem
        ): Boolean {
            return oldItem == newItem
        }

    }
}