package com.mx.rockstar.marsroversgallery.utils

import androidx.lifecycle.MutableLiveData

fun <T : Any?> MutableLiveData<T>.default(initialValue: T) = apply {
    if (initialValue != null) value = initialValue
}
