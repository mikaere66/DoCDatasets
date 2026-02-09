package com.michaelrmossman.docdatasets.util

import android.content.Context
import android.util.Log
import com.michaelrmossman.docdatasets.model.FeatureCollection
import kotlinx.serialization.json.Json
import java.io.FileNotFoundException
import java.io.IOException

/**
 * Custom json utilities used throughout the app
 */
class FeatureCollectionDataSerializer(private val context: Context) {

    companion object {
        const val TAG = "FeatureCollectionDataSerializer"
    }

    fun deserializeFeatureCollectionFromFile(
        filename: String
    ) : FeatureCollection? {
        try {
            context.openFileInput(filename).use { inputStream ->
                val size = inputStream.available()
                val buffer = ByteArray(size)
                inputStream.read(buffer)
                inputStream.close()
                val jsonString = String(buffer)
                return Json.decodeFromString(
                    FeatureCollection.serializer(), jsonString
                )
            }

        } catch (ex: FileNotFoundException) {
            // File not found
            ex.printStackTrace()
            return null

        } catch (ex: IOException) {
            // I/O error
            ex.printStackTrace()
            return null
        }
    }

    fun serializeFeatureCollectionToFile(
        collection: FeatureCollection,
        filename: String
    ) : Boolean {
        val jsonString = Json.encodeToString(
            FeatureCollection.serializer(),collection
        )
        return try {
            context.openFileOutput(
                filename, Context.MODE_PRIVATE
            ).use { outputStream ->
                outputStream.write(jsonString.toByteArray())
            }
            if (DEBUG_SHOW_ADDITIONAL_MESSAGES) {
                Log.d(TAG,"File saved to: ".plus(
                    filename
                ))
            }
            true

        } catch (ex: IOException) {
            // I/O error
            ex.printStackTrace()
            false
        }
    }
}