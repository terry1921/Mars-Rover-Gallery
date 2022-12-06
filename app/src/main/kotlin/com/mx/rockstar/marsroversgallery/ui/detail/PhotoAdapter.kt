package com.mx.rockstar.marsroversgallery.ui.detail

import android.os.SystemClock
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.NO_POSITION
import com.mx.rockstar.core.model.Photo
import com.mx.rockstar.marsroversgallery.R
import com.mx.rockstar.marsroversgallery.databinding.ItemPhotoBinding
import com.skydoves.bindables.BindingListAdapter
import com.skydoves.bindables.binding
import timber.log.Timber

class PhotoAdapter : BindingListAdapter<Photo, PhotoAdapter.PhotoViewHolder>(diffUtil) {

    private var onClickedAt = 0L

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder =
        parent.binding<ItemPhotoBinding>(R.layout.item_photo).let(::PhotoViewHolder)

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) =
        holder.bindPhoto(getItem(position))

    inner class PhotoViewHolder constructor(private val binding: ItemPhotoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position =
                    bindingAdapterPosition.takeIf { it != NO_POSITION } ?: return@setOnClickListener
                val currentClickedAt = SystemClock.elapsedRealtime()
                if (currentClickedAt - onClickedAt > binding.transformationLayout.duration) {
                    Timber.d("item: ${getItem(position)}")
                    onClickedAt = currentClickedAt
                }
            }
        }

        fun bindPhoto(photo: Photo) {
            TODO("Not yet implemented")
        }
    }

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<Photo>() {
            override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean =
                oldItem == newItem
        }
    }
}