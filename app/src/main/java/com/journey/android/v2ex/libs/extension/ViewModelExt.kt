package com.journey.android.v2ex.libs.extension

import androidx.lifecycle.viewModelScope
import com.journey.android.v2ex.base.BaseViewModel
import com.orhanobut.logger.Logger
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun BaseViewModel.launch(
  block: suspend CoroutineScope.() -> Unit,
  onError: (e: Throwable) -> Unit = {},
  onComplete: () -> Unit = {}
) {
  viewModelScope.launch(CoroutineExceptionHandler { _, e ->
    Logger.e(e.message ?: "")
    onError(e)
  }) {
    try {
      block.invoke(this)
    } finally {
      onComplete()
    }
  }
}

