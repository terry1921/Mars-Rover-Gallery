package com.mx.rockstar.marsroversgallery.ui.main

import android.os.SystemClock
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.NO_POSITION
import com.mx.rockstar.core.model.Rover
import com.mx.rockstar.marsroversgallery.R
import com.mx.rockstar.marsroversgallery.databinding.ItemRoverBinding
import com.skydoves.bindables.BindingListAdapter
import com.skydoves.bindables.binding
import timber.log.Timber

class RoverAdapter : BindingListAdapter<Rover, RoverAdapter.RoverViewHolder>(diffUtil) {

    private var onClickedAt = 0L

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoverViewHolder =
        parent.binding<ItemRoverBinding>(R.layout.item_rover).let(::RoverViewHolder)

    override fun onBindViewHolder(holder: RoverViewHolder, position: Int) =
        holder.bindRover(getItem(position))

    inner class RoverViewHolder constructor(private val binding: ItemRoverBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position =
                    bindingAdapterPosition.takeIf { it != NO_POSITION } ?: return@setOnClickListener
                val currentClickedAt = SystemClock.elapsedRealtime()
                if (currentClickedAt - onClickedAt > binding.transformationLayout.duration) {
                    // call another activity
                    Timber.d("item ${getItem(position)}")
                    onClickedAt = currentClickedAt
                }

            }
        }

        fun bindRover(rover: Rover) {
            binding.rover = rover
            binding.executePendingBindings()
        }

    }

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<Rover>() {
            override fun areItemsTheSame(oldItem: Rover, newItem: Rover): Boolean =
                oldItem.name == newItem.name

            override fun areContentsTheSame(oldItem: Rover, newItem: Rover): Boolean =
                oldItem == newItem
        }
    }
}