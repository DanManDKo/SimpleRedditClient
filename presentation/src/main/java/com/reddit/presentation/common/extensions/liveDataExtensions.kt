package com.reddit.presentation.common.extensions

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.reddit.presentation.common.databinding.OneTimeActionWithParameter

/**
 * Created with Android Studio.
 * User: Danil Konovalenko
 * Date: 2020-01-19
 * Time: 14:31
 */

fun <T> LiveData<T>.defaultObserve(owner: LifecycleOwner, observer: (t: T) -> Unit) =
    this.observe(owner, Observer { it.let(observer) })

fun <T1> observeForever(
    p1: LiveData<T1>,
    block: (T1) -> Unit
)
        : OneTimeActionWithParameter<T1> {

    val action =
        OneTimeActionWithParameter<T1>() {
            block(it)
        }

    p1.observeForever {
        action.invoke(it)
    }

    return action
}