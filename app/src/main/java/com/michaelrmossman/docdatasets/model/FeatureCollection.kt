package com.michaelrmossman.docdatasets.model

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class FeatureCollection(

    val crs: Crs = Crs(),
    val features: List<Feature> = emptyList(),
    val properties: CollectionProperties? = null,
    val type: String = String()
)