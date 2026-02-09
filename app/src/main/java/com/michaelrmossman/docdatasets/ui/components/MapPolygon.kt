package com.michaelrmossman.docdatasets.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Polygon
import com.google.maps.android.compose.MapsComposeExperimentalApi
import com.google.maps.android.compose.Polygon
import com.michaelrmossman.docdatasets.enum.PolygonType
import com.michaelrmossman.docdatasets.util.MAP_POLYGON_STROKE_NARROW
import com.michaelrmossman.docdatasets.util.MAP_POLYGON_STROKE_THICK

@OptIn(MapsComposeExperimentalApi::class)
@Composable
fun MapPolygon(
    color: Color,
    coordsList: List<List<List<LatLng>>>,
    onClickPolygon: (Polygon) -> Unit,
    polygonType: PolygonType,
    single: Boolean
) {
    coordsList.forEach { innerList ->

        innerList.forEach { latLngList ->

            Polygon(
                clickable = true,
                fillColor = color,
                points = latLngList,
                onClick = { polygon ->
                    println("Polygon clicked")
                    onClickPolygon(polygon)
                },
                strokeWidth = when (single) {
                    true -> MAP_POLYGON_STROKE_THICK
                    else -> MAP_POLYGON_STROKE_NARROW
                },
                tag = polygonType
            )
        }
    }
}