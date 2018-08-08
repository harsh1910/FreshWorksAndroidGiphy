package com.freshworks.freshworksandroidgiphy.interfaces

import android.content.Context

interface ISharedPreference {
    fun setLocalDirPath(context: Context, dirPath: String)
    fun getLocalDirPath(context: Context):String
}