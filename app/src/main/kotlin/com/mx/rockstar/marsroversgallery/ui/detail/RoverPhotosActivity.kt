package com.mx.rockstar.marsroversgallery.ui.detail

import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.VisibleForTesting
import com.mx.rockstar.core.model.Rover
import com.mx.rockstar.marsroversgallery.R
import com.mx.rockstar.marsroversgallery.databinding.ActivityRoverPhotosBinding
import com.skydoves.bindables.BindingActivity
import com.skydoves.bundler.bundleNonNull
import com.skydoves.bundler.intentOf
import com.skydoves.transformationlayout.TransformationCompat
import com.skydoves.transformationlayout.TransformationLayout
import com.skydoves.transformationlayout.onTransformationStartContainer
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RoverPhotosActivity :
    BindingActivity<ActivityRoverPhotosBinding>(R.layout.activity_rover_photos) {

    @set:Inject
    internal lateinit var roverPhotoViewModel: RoverPhotoViewModel.AssistedFactory

    @get:VisibleForTesting
    internal val viewModel: RoverPhotoViewModel by viewModels {
        RoverPhotoViewModel.provideFactory(roverPhotoViewModel, roverItem)
    }

    private val roverItem: Rover by bundleNonNull(EXTRA_ROVER)

    override fun onCreate(savedInstanceState: Bundle?) {
        onTransformationStartContainer()
        super.onCreate(savedInstanceState)
        binding {
            rover = roverItem
            vm = viewModel
            adapter = PhotoAdapter()
        }
    }

    companion object {
        @VisibleForTesting
        internal const val EXTRA_ROVER = "EXTRA_ROVER"

        fun startActivity(transformationLayout: TransformationLayout, rover: Rover) =
            transformationLayout.context.intentOf<RoverPhotosActivity> {
                putExtra(EXTRA_ROVER to rover)
                TransformationCompat.startActivity(transformationLayout, intent)
            }
    }

}