package com.mx.rockstar.core.net

import javax.inject.Qualifier
import kotlin.annotation.AnnotationRetention.RUNTIME

@Qualifier
@Retention(RUNTIME)
annotation class Dispatcher(val appDispatcher: AppDispatcher)

enum class AppDispatcher {
    IO
}