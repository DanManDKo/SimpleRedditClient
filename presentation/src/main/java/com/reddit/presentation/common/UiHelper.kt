package com.reddit.presentation.common

import android.app.Activity
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.reddit.presentation.R
import javax.inject.Inject

/**
 * Created with Android Studio.
 * User: Danil Konovalenko
 * Date: 2020-01-18
 * Time: 19:38
 */
class UiHelper
@Inject
constructor  (private val activity: Activity) {
    private var errorToast: Toast? = null
    private var uploadingDialog: AlertDialog? = null

    fun showErrorToast(message: String?) {
        if (errorToast != null) {
            errorToast!!.cancel()
        }
        errorToast = Toast.makeText(activity, message, Toast.LENGTH_LONG)
        errorToast!!.show()
    }

    private fun showUploading() {
        if (uploadingDialog == null) {
            uploadingDialog = AlertDialog.Builder(activity)
                .setView(R.layout.view_loading_dialog)
                .show()
            uploadingDialog!!.window!!.setBackgroundDrawableResource(android.R.color.transparent)
            uploadingDialog!!.setCancelable(false)
        } else {
            uploadingDialog!!.show()
        }
    }

    private fun hideUploading() {
        if (uploadingDialog != null && uploadingDialog?.window?.decorView?.parent != null) {
            uploadingDialog!!.dismiss()
        }
    }

    fun showUploading(uploadingState: Boolean) {
        if (uploadingState) {
            showUploading()
        } else {
            hideUploading()
        }
    }

    fun showMessage(message: String) {
        if (errorToast != null) {
            errorToast!!.cancel()
        }
        errorToast = Toast.makeText(activity, message, Toast.LENGTH_LONG)
        errorToast!!.show()
    }
}