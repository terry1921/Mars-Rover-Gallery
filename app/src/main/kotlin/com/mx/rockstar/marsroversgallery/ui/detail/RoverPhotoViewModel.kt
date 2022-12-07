package com.mx.rockstar.marsroversgallery.ui.detail

import androidx.annotation.MainThread
import androidx.databinding.Bindable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.mx.rockstar.core.data.repository.PhotoRepository
import com.mx.rockstar.core.model.Camera
import com.mx.rockstar.core.model.Photo
import com.mx.rockstar.core.model.Rover
import com.mx.rockstar.core.model.mapper.asCapsule
import com.skydoves.bindables.BindingViewModel
import com.skydoves.bindables.asBindingProperty
import com.skydoves.bindables.bindingProperty
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import timber.log.Timber

class RoverPhotoViewModel @AssistedInject constructor(
    photosRepository: PhotoRepository,
    @Assisted private val rover: Rover
) : BindingViewModel() {

    @get:Bindable
    var isLoading: Boolean by bindingProperty(false)
        private set

    @get:Bindable
    var isLast: Boolean by bindingProperty(false)
        private set

    @get:Bindable
    var toastMessage: String? by bindingProperty(null)
        private set

    private val camera = MutableLiveData<Camera>()

    private val photoFetchingIndex: MutableStateFlow<Int> = MutableStateFlow(1)
    private val photosListFlow = photoFetchingIndex.flatMapLatest { page ->
        photosRepository.fetchPhotos(
            sol = 1,
            rover = rover.asCapsule(),
            camera = camera.value,
            page = page,
            onStart = { isLoading = true },
            onComplete = { lastSize ->
                if (lastSize < 25) isLast = true
                isLoading = false
            },
            onError = { toastMessage = it }
        )
    }

    @get:Bindable
    val photoList: List<Photo> by photosListFlow.asBindingProperty(viewModelScope, emptyList())

    init {
        Timber.d("init RoverPhotoViewModel")
    }

    @MainThread
    fun fetchNextPhotoList() {
        if (!isLoading) {
            photoFetchingIndex.value++
        }
    }

    @dagger.assisted.AssistedFactory
    interface AssistedFactory {
        fun create(rover: Rover): RoverPhotoViewModel
    }

    companion object {
        fun provideFactory(
            assistedFactory: AssistedFactory,
            rover: Rover
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {

            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(rover) as T
            }
        }
    }

}