package com.freshworks.freshworksandroidgiphy.utils

import android.content.Context
import android.content.ContextWrapper
import com.freshworks.freshworksandroidgiphy.interfaces.IFileDownloadListener
import com.koushikdutta.async.future.FutureCallback
import com.koushikdutta.ion.Ion
import java.io.File

class FileHelper {

    fun getFavouriteGifsList(context: Context): Array<File>? {
        val cw = ContextWrapper(context)
        val directory = cw.getDir(DIR_NAME, Context.MODE_PRIVATE)
        return directory.listFiles()
    }

    fun deleteFile(context: Context, fileName: String?): Boolean {
        val cw = ContextWrapper(context)
        val directory = cw.getDir(DIR_NAME, Context.MODE_PRIVATE)
        val file = File(directory, fileName + FILE_EXTENSION)
        return file.delete()
    }

    fun downloadingFile(context: Context, fileName: String?, url: String?, callBack: IFileDownloadListener) {
        val cw = ContextWrapper(context)
        val directory = cw.getDir(DIR_NAME, Context.MODE_PRIVATE)
        val gifFilePath = File(directory, fileName + FILE_EXTENSION)
        Ion.with(context).load(url)
                .write(gifFilePath)
                .setCallback(FutureCallback<File> { e, _ ->
                    if (e != null) {
                        callBack.downloadFailed()
                    }
                })

    }

    fun deleteFavouriteGifFile(gifFile: File): Boolean {
        return gifFile.delete()
    }
}
