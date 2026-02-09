package com.michaelrmossman.docdatasets

import android.app.Application
import com.michaelrmossman.docdatasets.R
import com.michaelrmossman.docdatasets.data.AppContainer
import com.michaelrmossman.docdatasets.data.DefaultAppContainer
import com.michaelrmossman.docdatasets.util.FileUtils.unzipRawToFilesDir

class DoCDataSetsApplication: Application() {

    /* AppContainer instance, used to obtain dependencies */
    lateinit var container: AppContainer

    companion object {

        lateinit var instance: DoCDataSetsApplication
    }

    override fun onCreate() {
        super.onCreate()
        /* Returns: /storage/emulated/0/Android/data/com.michaelrmossman.docdatasets/files
        android.util.Log.d("HEY","${ this.getExternalFilesDir(null)?.absolutePath }") */

        container = DefaultAppContainer(applicationContext)

        instance = this
    }
}