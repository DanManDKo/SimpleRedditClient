package com.reddit.presentation.common

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.reddit.presentation.common.databinding.SingleLiveEvent
import com.reddit.presentation.utils.RxDisposable

/**
 * Created with Android Studio.
 * User: Danil Konovalenko
 * Date: 2020-01-18
 * Time: 17:37
 */
open class BaseViewModel : ViewModel() {

    var errorMessage = SingleLiveEvent<String>()
    var uploading = MutableLiveData<Boolean>()

    override fun onCleared() {
        super.onCleared()
        RxDisposable.unsubscribe(this)
    }
}