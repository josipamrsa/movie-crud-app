package com.jmrsa.moviecrudapp.presentation.fragments.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jmrsa.moviecrudapp.utils.isNull
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class BaseViewModel : ViewModel() {
    protected fun launchIn(
        coroutineScope: CoroutineScope = viewModelScope,
        action: suspend () -> Unit
    ) {
        coroutineScope.launch {
            action()
        }
    }

    protected fun launchWithProgress(
        coroutineScope: CoroutineScope = viewModelScope,
        onStart: () -> Unit = {},
        onFinish: () -> Unit = {},
        action: suspend () -> Unit
    ) {
        launchIn(
            coroutineScope = coroutineScope
        ) {
            onStart()
            action()
            onFinish()
        }
    }

    protected fun launchInContext(
        coroutineScope: CoroutineScope = viewModelScope,
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        action: suspend () -> Unit
    ) {
        launchIn(
            coroutineScope = coroutineScope
        ) {
            withContext(dispatcher) {
                action()
            }
        }
    }

    protected fun launchInContextWithProgress(
        coroutineScope: CoroutineScope = viewModelScope,
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        onStart: () -> Unit = {},
        onFinish: () -> Unit = {},
        action: suspend () -> Unit
    ) {
        launchWithProgress(
            coroutineScope = coroutineScope,
            onStart = onStart,
            onFinish = onFinish
        ) {
            withContext(dispatcher) {
                action()
            }
        }
    }

    protected fun <T: BaseViewState> MutableLiveData<T>.update(transform: (T) -> T) {
        this.value.let { current ->
            require(current.isNull().not()) { "ViewState LiveData value cannot be null" }
            this.value = current?.let { transform(it) }
        }
    }
}