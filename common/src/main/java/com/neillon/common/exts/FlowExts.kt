package com.neillon.common.exts

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

fun <T> Flow<T>.launchAndCollectIn(lifecycleOwner: LifecycleOwner, block: (T) -> Unit) {
    lifecycleOwner.lifecycleScope.launch {
        collect { block(it) }
    }
}