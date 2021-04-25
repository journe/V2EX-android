package com.journey.android.v2ex.libs.extension

import androidx.lifecycle.lifecycleScope
import com.journey.android.v2ex.base.BaseCoroutinesException
import com.journey.android.v2ex.base.BaseFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Created by journey on 4/25/21.
 */
fun BaseFragment.launch(
  block: suspend CoroutineScope.() -> Unit,
  onError: (e: Throwable) -> Unit = {},
  onComplete: () -> Unit = {}
) {
  lifecycleScope.launch(BaseCoroutinesException()) {
    try {
      block.invoke(this)
    } catch (e: Throwable) {
      onError(e)
    } finally {
      onComplete()
    }
  }
}

