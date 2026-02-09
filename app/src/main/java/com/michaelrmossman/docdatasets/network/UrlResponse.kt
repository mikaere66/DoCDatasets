package com.michaelrmossman.docdatasets.network

import com.michaelrmossman.docdatasets.model.FeatureCollection

data class UrlResponse(

    val collection: FeatureCollection? = null,

    // val filename: String? = null,

    val responseMessage: String? = null,

    val responseCode: Int = 0
)