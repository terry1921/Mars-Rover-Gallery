package com.mx.rockstar.marsroversgallery.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.VisibleForTesting
import com.mx.rockstar.marsroversgallery.R
import com.mx.rockstar.marsroversgallery.databinding.ActivityMainBinding
import com.skydoves.bindables.BindingActivity
import com.skydoves.transformationlayout.onTransformationStartContainer
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BindingActivity<ActivityMainBinding>(R.layout.activity_main) {

    @get:VisibleForTesting
    internal val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        onTransformationStartContainer()
        super.onCreate(savedInstanceState)
        binding {
            vm = viewModel
            adapter = RoverAdapter()
            lifecycleOwner = this@MainActivity
        }
    }
}