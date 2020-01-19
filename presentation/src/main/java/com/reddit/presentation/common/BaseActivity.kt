package com.reddit.presentation.common

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import javax.inject.Inject

/**
 * Created with Android Studio.
 * User: Danil Konovalenko
 * Date: 2020-01-18
 * Time: 19:17
 */
open class BaseActivity<BINDING : ViewDataBinding, VM : BaseViewModel>
    (
    @LayoutRes private val layoutId: Int,
    private val viewModelClass: Class<VM>
) : AppCompatActivity() {

    protected lateinit var binding: BINDING
    protected lateinit var vm: VM

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    @Inject
    lateinit var uiHelper: UiHelper

    open fun inject(fragment: Fragment) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm = ViewModelProviders.of(this, viewModelFactory).get(viewModelClass)
        binding = DataBindingUtil.setContentView(this, layoutId)
        binding.lifecycleOwner = this
        defaultObserve()
    }

    private fun defaultObserve() {
        vm.errorMessage.observe(this, Observer {
            uiHelper.showErrorToast(it)
        })
        vm.uploading.observe(this, Observer {
            uiHelper.showUploading(it)
        })
    }
}