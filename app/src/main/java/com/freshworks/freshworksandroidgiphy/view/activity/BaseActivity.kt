package com.freshworks.freshworksandroidgiphy.view.activity

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.freshworks.freshworksandroidgiphy.utils.DialogHelper

open class BaseActivity : AppCompatActivity() {

    private var internetReceiver: InternetReceiver? = null
    private val CONNECTIVITY_CHANGE = "android.net.conn.CONNECTIVITY_CHANGE"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

//    register receiver
    override fun onResume() {
        super.onResume()
        if (internetReceiver == null) {
            internetReceiver = InternetReceiver()
            registerReceiver(internetReceiver, IntentFilter(CONNECTIVITY_CHANGE))
        }
    }

//    unregister receiver
    override fun onPause() {
        super.onPause()
        if (internetReceiver != null) {
            unregisterReceiver(internetReceiver)
            internetReceiver = null
        }
    }

//    broadcast receiver for check internet connection
    inner class InternetReceiver : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            showDialog(!checkConnectivity(context))
        }
    }

    // check for internet connectvity
    fun checkConnectivity(context: Context): Boolean {
        try {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val info = cm.activeNetworkInfo
            if (info == null) {
                return false
            } else if (info.isConnected) {
                return true
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return false
    }


    //    show no internet dialog
    protected fun showDialog(isConnected: Boolean) {
        if (isConnected) {
            DialogHelper.showNoConnectionDialog(this)
        }
    }

}

