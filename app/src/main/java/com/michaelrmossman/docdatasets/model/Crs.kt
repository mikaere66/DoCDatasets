package com.michaelrmossman.docdatasets.model

import kotlinx.serialization.Serializable

@Serializable
data class Crs(

    val properties: CrsProperties = CrsProperties(),

    val type: String = String()
)