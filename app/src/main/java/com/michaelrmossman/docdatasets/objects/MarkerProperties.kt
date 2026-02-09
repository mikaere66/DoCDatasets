package com.michaelrmossman.docdatasets.objects

import kotlinx.serialization.Serializable

@Serializable
data class MarkerProperties(

    val lat: Double,

    val lon: Double,

    val snippet: String,

    val title: String
)