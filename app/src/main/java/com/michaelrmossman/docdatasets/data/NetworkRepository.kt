package com.michaelrmossman.docdatasets.data

import android.content.Context
import com.michaelrmossman.docdatasets.model.FeatureCollection
import com.michaelrmossman.docdatasets.network.DoCDataSetsApiService
import com.michaelrmossman.docdatasets.network.UrlResponse
import com.michaelrmossman.docdatasets.util.DOC_FORMAT_VAL
import com.michaelrmossman.docdatasets.util.DOC_OBJECT_ID
import com.michaelrmossman.docdatasets.util.DOC_OUT_F_VAL
import com.michaelrmossman.docdatasets.util.DOC_WHERE_VAL
import com.michaelrmossman.docdatasets.util.FeatureCollectionDataSerializer
import java.io.File
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.io.path.Path
import kotlin.io.path.deleteIfExists

class NetworkRepository(
    private val apiService: DoCDataSetsApiService
) {

    companion object { const val TAG = "NetworkRepository" }

    fun getCollectionCount(
        callback: (UrlResponse) -> Unit,
        resId: String,
        resLayer: Int
    ) {
        val call = apiService.getCollectionCount(
            resId = resId,
            resLayer = resLayer,
            outFields = DOC_OUT_F_VAL,
            where = DOC_WHERE_VAL,
            countOnly = true,
            format = DOC_FORMAT_VAL
        )
        call.enqueue(object: Callback<FeatureCollection> {
            override fun onResponse(
                call:     Call<FeatureCollection>,
                response: Response<FeatureCollection>
            ) {
                if (response.errorBody() != null) {
                    callback(
                        UrlResponse(
                            responseCode = response.code()
                        )
                    )

                } else if (response.isSuccessful) {

                    response.body()?.let { responseBody ->
                        callback(
                            UrlResponse(
                                collection = responseBody,
                                responseCode = response.code()
                            )
                        )
                    }
                }
            }

            override fun onFailure(
                call: Call<FeatureCollection>,
                throwable: Throwable
            ) {
                callback(UrlResponse(responseCode = 0))
                println(throwable)
            }
        })
    }

    fun getFeatureCollection(
        callback: (UrlResponse) -> Unit,
        context: Context,
        filename: String,
        limit: Int,
        offset: Int,
        resId: String,
        resLayer: Int,
        updateDataset: Boolean
    ) {
        val call = apiService.getFeatureCollection(
            resId = resId,
            resLayer = resLayer,
            outFields = DOC_OUT_F_VAL,
            where = DOC_WHERE_VAL,
            format = DOC_FORMAT_VAL,
            geometry = false,
            limit = limit,
            offset = offset
        )
        call.enqueue(object: Callback<FeatureCollection> {
            override fun onResponse(
                call:     Call<FeatureCollection>,
                response: Response<FeatureCollection>
            ) {
                if (response.errorBody() != null) {
                    callback(
                        UrlResponse(
                            responseCode = response.code(),
                            responseMessage =
                                response.errorBody()?.toString()
                        )
                    )

                } else if (response.isSuccessful) {

                    response.body()?.let { responseBody ->

                        if (updateDataset) {
                            val file = File(context.filesDir,filename)
                            Path(file.absolutePath).deleteIfExists()
                        }

                        val serializer =
                            FeatureCollectionDataSerializer(context)
                        val existingCollection =
                            serializer.deserializeFeatureCollectionFromFile(
                                filename = filename
                            )
                        serializer.serializeFeatureCollectionToFile(
                            collection = when (existingCollection) {
                                null -> responseBody
                                else -> {
                                    val existingFeatures =
                                        existingCollection.features
                                    FeatureCollection(
                                        crs = existingCollection.crs,
                                        existingFeatures.plus(
                                            responseBody.features
                                        )
                                    )
                                }
                            },
                            filename = filename
                        )

                        callback(
                            UrlResponse(
                                collection = responseBody,
                                responseCode = response.code()
                            )
                        )
                    }

                } else {
                    callback(
                        UrlResponse(
                            responseCode = response.code()
                        )
                    )
                }
            }

            override fun onFailure(
                call: Call<FeatureCollection>, throwable: Throwable
            ) {
                callback(UrlResponse(
                    responseMessage = throwable.message,
                    responseCode = 0)
                )
                println(throwable)
            }
        })
    }

    fun getGeometryByObjectId(
        callback: (UrlResponse) -> Unit,
        objectId: Int,
        outFields: String,
        resId: String,
        resLayer: Int
    ) {
        val call = apiService.getGeometryByObjectId(
            resId = resId,
            resLayer = resLayer,
            outFields = outFields,
            where = "$DOC_OBJECT_ID=$objectId",
            format = DOC_FORMAT_VAL,
            geometry = true,
            outSR = 4326
        )
        call.enqueue(object: Callback<FeatureCollection> {
            override fun onResponse(
                call:     Call<FeatureCollection>,
                response: Response<FeatureCollection>
            ) {
                if (response.isSuccessful) {

                    response.body()?.let { responseBody ->
                        callback(
                            UrlResponse(
                                collection = responseBody,
                                responseCode = response.code()
                            )
                        )
                    }
                }
            }

            override fun onFailure(
                call: Call<FeatureCollection>, throwable: Throwable
            ) {
                callback(UrlResponse(responseCode = 0))
                println(throwable)
            }
        })
    }
}