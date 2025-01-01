package com.kareem.registrationsdk.presentation.core.base

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch


abstract class UiEffect

/**
 * Generic base class for ViewModels using a unidirectional data flow approach.
 * @param S The state type for this ViewModel.
 * @param E The event type for incoming user actions or triggers.
 */
abstract class BaseViewModel<S, E>(state: S, event: E?) : ViewModel() {


    open var state by mutableStateOf(state)

    abstract fun onEvent(event: E)

    // Flow of one-time events like navigation or toasts (UI side effects)
    private val _effectFlow = MutableSharedFlow<UiEffect>()
    val effectFlow: SharedFlow<UiEffect> = _effectFlow

    protected fun setEffect(builder: () -> UiEffect) {
        viewModelScope.launch {
            val effect = builder()
            _effectFlow.emit(effect)
        }
    }

}
