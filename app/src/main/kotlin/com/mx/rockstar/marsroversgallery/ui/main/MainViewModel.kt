package com.mx.rockstar.marsroversgallery.ui.main

import androidx.databinding.Bindable
import androidx.lifecycle.viewModelScope
import com.mx.rockstar.core.data.repository.RoverRepository
import com.mx.rockstar.core.model.Rover
import com.skydoves.bindables.BindingViewModel
import com.skydoves.bindables.asBindingProperty
import com.skydoves.bindables.bindingProperty
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val roverRepository: RoverRepository
) : BindingViewModel() {

    @get:Bindable
    var isLoading: Boolean by bindingProperty(false)
        private set

    @get:Bindable
    var toastMessage: String? by bindingProperty(null)
        private set

    private val roverFetchingIndex: MutableStateFlow<Int> = MutableStateFlow(0)
    private val roverListFlow = roverFetchingIndex.flatMapLatest {
        roverRepository.fetchRovers(
            onStart = { isLoading = true },
            onComplete = { isLoading = false },
            onError = { toastMessage = it }
        )
    }

    @get:Bindable
    val roverList: List<Rover> by roverListFlow.asBindingProperty(viewModelScope, emptyList())

    init {
        Timber.d("Init MainViewModel")
    }

}