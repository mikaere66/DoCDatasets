package com.michaelrmossman.docdatasets.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Polygon
import com.google.android.gms.maps.model.Polyline
import com.michaelrmossman.docdatasets.data.DataSetEntity
import com.michaelrmossman.docdatasets.enum.PolygonType
import com.michaelrmossman.docdatasets.enum.PolylineType
import com.michaelrmossman.docdatasets.model.Feature
import com.michaelrmossman.docdatasets.objects.MarkerProperties
import com.michaelrmossman.docdatasets.ui.components.MapMarker
import com.michaelrmossman.docdatasets.ui.components.MapPolygon
import com.michaelrmossman.docdatasets.ui.components.MapPolyline

/**
 * Google Maps utility functions used throughout the app
 */
object MapUtils {

    val markerContent: (
        @Composable (
            DataSetEntity, Feature, Double, Double, BitmapDescriptor
        ) -> Unit
    ) = { dataset, feature, latitude, longitude, placeIcon ->
        MapMarker(
            placeIcon = placeIcon,
            onMarkerClick = { _ -> true },
            properties = MarkerProperties(
                lat = latitude,
                lon = longitude,
                snippet = feature.mapMarkerSnippet(dataset.dataSet),
                title = feature.mapMarkerTitle(dataset.dataSet)
            )
        )
    }

//    private val polygonColors = randomlyGeneratedColors(
//        alpha = MAP_POLYGON_FILL_ALPHA
//    ) // TODO
    val polygonContent: (
        @Composable (
            List<List<List<LatLng>>>, Feature,
            (Feature, Polygon) -> Unit, PolygonType, Boolean
        ) -> Unit
    ) = { coords, feature, onClick, polyType, single ->
        MapPolygon(
            color = Color(0x330000FF),
//            (
//            polygonColors[
//                feature.properties.objectId
//            ]
//            ),
            coordsList = coords,
            onClickPolygon = { polygon ->
                onClick(feature, polygon)
            },
            polygonType = polyType,
            single = single
        )
    }

    val polylineContent: (
        @Composable (
            List<List<LatLng>>, Feature,
            PolylineType, (Feature, Polyline) -> Unit
        ) -> Unit
    ) = { coords, feature, polyType, onClick ->
        MapPolyline(
            coordsList = coords,
            onClickPolyline = { polyline ->
                onClick(feature, polyline)
            },
            polylineType = polyType
        )
    }
}