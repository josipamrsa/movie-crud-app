package com.jmrsa.moviecrudapp.presentation.fragments.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jmrsa.moviecrudapp.utils.isNull
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

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

    protected fun <T: BaseViewState> MutableLiveData<T>.update(transform: (T) -> T) {
        this.value.let { current ->
            require(current.isNull().not()) { "ViewState LiveData value cannot be null" }
            this.value = current?.let { transform(it) }
        }
    }
}