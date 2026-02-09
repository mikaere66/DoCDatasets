package com.michaelrmossman.docdatasets.util

import android.content.Context
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream

/**
 * File utility functions used throughout the app
 */
object FileUtils {

    fun getDataSetFilename(
        resId: String,
        resLayer: Int
    ) : String {
        /* Curly brackets reqd because of special chars */
        return "${ resId }_${ resLayer }.$DOC_FORMAT_VAL"
    }

    fun getDataSetPdfFile(
        context: Context,
        filename: String
    ) : File {
        val appContext = context.applicationContext
        return File(appContext.filesDir,filename)
    }

    fun getInternalDbPath(
        context: Context,
        filename: String
    ): String {
        val appContext = context.applicationContext
        val dbFile = appContext.getDatabasePath(filename)
        return dbFile.absolutePath
    }

    // e.g. unzipRawToFilesDir(context, R.raw.geojson_under_1_mb)
    fun unzipRawToFilesDir(context: Context, zipRawResId: Int) {
        val inputStream = context.resources.openRawResource(zipRawResId)
        val zipInputStream = ZipInputStream(inputStream)
        var entry: ZipEntry?

        try {
            while (zipInputStream.nextEntry.also { entry = it } != null) {
                val entryName = entry?.name
                if (entryName != null && !entry.isDirectory) {
                    val outputFile = File(context.filesDir,entryName)
                    // Ensure parent directories for nested files exist
                    outputFile.parentFile?.mkdirs()

                    val fos = FileOutputStream(outputFile)
                    zipInputStream.copyTo(fos)
                    fos.close()
                }
                zipInputStream.closeEntry()
            }

        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            zipInputStream.close()
            inputStream.close()
        }
    }
}