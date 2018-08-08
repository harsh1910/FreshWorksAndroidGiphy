package com.freshworks.freshworksandroidgiphy.network

import android.content.Context
import com.freshworks.freshworksandroidgiphy.FreshWorksAndroidGiphyApplication
import com.freshworks.freshworksandroidgiphy.utils.FileHelper

class ServiceRegistry {

    companion object {
        private val ourInstance = ServiceRegistry()

        fun getInstance(): ServiceRegistry {
            return ourInstance
        }
    }

    private var contextApplication: Context? = null


    var apiService: ApiInterface? = null

    var fileHelper: FileHelper? = null


    fun getApi(): ApiInterface {
        apiService = ApiClient.getClient().create(ApiInterface::class.java)
        return apiService as ApiInterface
    }

    fun getFileManager(): FileHelper {
        fileHelper = FileHelper()
        return fileHelper as FileHelper
    }

    fun getContextApplication(): Context {
        if (contextApplication == null) {
            contextApplication = FreshWorksAndroidGiphyApplication.instance
        }
        return contextApplication!!
    }

}