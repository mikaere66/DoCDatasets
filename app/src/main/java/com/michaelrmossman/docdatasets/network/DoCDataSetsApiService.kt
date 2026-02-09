package com.michaelrmossman.docdatasets.network

import com.michaelrmossman.docdatasets.model.FeatureCollection
import com.michaelrmossman.docdatasets.util.DOC_BASE_PATH
import com.michaelrmossman.docdatasets.util.DOC_COUNT_ONLY
import com.michaelrmossman.docdatasets.util.DOC_FEATURE
import com.michaelrmossman.docdatasets.util.DOC_FORMAT
import com.michaelrmossman.docdatasets.util.DOC_GEOMETRY
import com.michaelrmossman.docdatasets.util.DOC_LAYER_VAL
import com.michaelrmossman.docdatasets.util.DOC_LIMIT_VAL
import com.michaelrmossman.docdatasets.util.DOC_OFFSET_VAL
import com.michaelrmossman.docdatasets.util.DOC_OUTFIELDS
import com.michaelrmossman.docdatasets.util.DOC_OUT_S_R
import com.michaelrmossman.docdatasets.util.DOC_WHERE
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface DoCDataSetsApiService {

    @GET(DOC_BASE_PATH)
    fun getCollectionCount(
        @Path (DOC_FEATURE)   resId: String,
        @Path (DOC_LAYER_VAL) resLayer: Int,
        @Query(DOC_OUTFIELDS) outFields: String,
        @Query(DOC_WHERE)     where: String,
        @Query(DOC_COUNT_ONLY)countOnly: Boolean,
        @Query(DOC_FORMAT)    format: String
    ) : Call<FeatureCollection>

    @GET(DOC_BASE_PATH)
    fun getFeatureCollection(
        @Path (DOC_FEATURE)   resId: String,
        @Path (DOC_LAYER_VAL) resLayer: Int,
        @Query(DOC_OUTFIELDS) outFields: String,
        @Query(DOC_WHERE)     where: String,
        @Query(DOC_FORMAT)    format: String,
        @Query(DOC_GEOMETRY)  geometry: Boolean,
        @Query(DOC_LIMIT_VAL) limit: Int,
        @Query(DOC_OFFSET_VAL)offset: Int
    ) : Call<FeatureCollection>

    @GET(DOC_BASE_PATH)
    fun getGeometryByObjectId(
        @Path (DOC_FEATURE)   resId: String,
        @Path (DOC_LAYER_VAL) resLayer: Int,
        @Query(DOC_OUTFIELDS) outFields: String,
        @Query(DOC_WHERE)     where: String,
        @Query(DOC_FORMAT)    format: String,
        @Query(DOC_GEOMETRY)  geometry: Boolean,
        @Query(DOC_OUT_S_R)   outSR: Int
    ) : Call<FeatureCollection>
}