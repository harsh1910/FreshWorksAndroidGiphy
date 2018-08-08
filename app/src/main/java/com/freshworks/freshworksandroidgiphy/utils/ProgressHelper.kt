package com.freshworks.freshworksandroidgiphy.utils

import android.app.Dialog
import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.app.ActionBar
import android.view.LayoutInflater
import android.view.Window
import com.freshworks.freshworksandroidgiphy.R
import com.freshworks.freshworksandroidgiphy.databinding.DialogProgressBinding


class ProgressHelper {
    companion object {

        var mDialog: Dialog? = null

        /*progress dialog*/
        fun showDialog(context: Context) {
            try {
                mDialog = Dialog(context)
                mDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
                mDialog!!.setCancelable(false)
                mDialog!!.window!!.setBackgroundDrawableResource(android.R.color.transparent)
                mDialog!!.setContentView(R.layout.dialog_progress)
                mDialog!!.window!!.setLayout(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT)
                mDialog!!.show()
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }

        /* dismissDialog is used for close progressDialog */
        fun dismissDialog() {
            try {
                if (mDialog != null && mDialog!!.isShowing) {
                    mDialog!!.dismiss()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }
}