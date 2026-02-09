package com.michaelrmossman.docdatasets.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/* To be registered automatically, the sub-class
   has to be '@Serializable', and the base
   class has to be sealed and '@Serializable' */
@Serializable
sealed interface Geometry {

    @SerialName("LineString")
    @Serializable
    data class LineString(
        val coordinates: List<List<Double>>
    ) : Geometry

    @SerialName("MultiLineString")
    @Serializable
    data class MultiLine(
        val coordinates: List<List<List<Double>>>
    ) : Geometry

    @SerialName("MultiPolygon")
    @Serializable
    data class MultiPolygon(
        val coordinates: List<List<List<List<Double>>>>
    ) : Geometry

    @SerialName("Point")
    @Serializable
    data class Point(
        val coordinates: List<Double>
    ) : Geometry

    @SerialName("Polygon")
    @Serializable
    data class Polygon(
        val coordinates: List<List<List<Double>>>
    ) : Geometry

    @SerialName("rings")
    @Serializable
    data class Rings(
        val coordinates: List<List<List<Double>>>
    ) : Geometry
}