package com.mx.rockstar.marsroversgallery.ui.detail

import androidx.databinding.Bindable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mx.rockstar.core.data.repository.PhotoRepository
import com.skydoves.bindables.BindingViewModel
import com.skydoves.bindables.bindingProperty
import dagger.assisted.AssistedInject
import timber.log.Timber


class RoverPhotoViewModel @AssistedInject constructor(
    photosRepositoryImpl: PhotoRepository,
) : BindingViewModel() {

    @get:Bindable
    var isLoading: Boolean by bindingProperty(false)
        private set

    @get:Bindable
    var toastMessage: String? by bindingProperty(null)
        private set

    init {
        Timber.d("init RoverPhotoViewModel")
    }

    @dagger.assisted.AssistedFactory
    interface AssistedFactory {
        fun create(): RoverPhotoViewModel
    }

    companion object {
        fun provideFactory(
            assistedFactory: AssistedFactory,
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {

            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create() as T
            }
        }
    }

}