package com.freshworks.freshworksandroidgiphy.utils

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.provider.Settings
import com.freshworks.freshworksandroidgiphy.R


class DialogHelper {
    companion object {
        fun showDialog(title: String, message: String, context: Context): AlertDialog {
            return showDialog(title, message, context, null)
        }

        fun showDialog(title: String, message: String, context: Context, listener: DialogInterface.OnClickListener?): AlertDialog {
            // 1. Instantiate an AlertDialog.Builder with its constructor
            val builder = AlertDialog.Builder(context)

            // 2. Chain together various setter methods to set the dialog characteristics
            builder.setMessage(message)
                    .setTitle(title)

            // 3. Get the AlertDialog from create()
            builder.setPositiveButton(context.getString(R.string.ok), listener)

            val dialog = builder.create()
            //        TextView messageText = (TextView)dialog.findViewById(android.R.id.message);
            //        messageText.setGravity(Gravity.CENTER);
            dialog.setCancelable(false)
            dialog.show()
            return dialog
        }

        //        no internet connection dialog
        fun showNoConnectionDialog(context: Context) {
            val builder = AlertDialog.Builder(context)
            builder.setCancelable(true)
            builder.setMessage(context.getString(R.string.no_internet_connection))
            builder.setTitle(context.getString(R.string.no_connection_title))
            builder.setPositiveButton(context.getString(R.string.settings), DialogInterface.OnClickListener { dialog, which -> context.startActivity(Intent(Settings.ACTION_WIRELESS_SETTINGS)) })
            builder.setNegativeButton(context.getString(R.string.cancel), DialogInterface.OnClickListener { dialog, which -> return@OnClickListener })
            builder.setOnCancelListener(DialogInterface.OnCancelListener { return@OnCancelListener })
            builder.show()
        }
    }
}