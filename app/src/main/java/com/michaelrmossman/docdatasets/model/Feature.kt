package com.michaelrmossman.docdatasets.model

import kotlinx.serialization.Serializable

@Serializable
data class Feature(

    val geometry: Geometry? = null,

    val id: Int = 0,

    val properties: Properties = Properties(),

    val type: String = String()
)