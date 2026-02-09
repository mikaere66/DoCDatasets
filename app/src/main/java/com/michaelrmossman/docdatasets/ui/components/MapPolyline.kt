package com.michaelrmossman.docdatasets.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Polyline
import com.google.maps.android.compose.MapsComposeExperimentalApi
import com.google.maps.android.compose.Polyline
import com.michaelrmossman.docdatasets.enum.PolylineType
import com.michaelrmossman.docdatasets.util.MAP_POLYLINE_WIDTH

@OptIn(MapsComposeExperimentalApi::class)
@Composable
fun MapPolyline(
    coordsList: List<List<LatLng>>,
    onClickPolyline: (Polyline) -> Unit,
    polylineType: PolylineType
) {
    coordsList.forEach { latLngList ->

        Polyline(
            points = latLngList,
            color = Color.Red,
            width = MAP_POLYLINE_WIDTH,
            clickable = true,
            onClick = { polyline ->
                println("Polyline clicked")
                onClickPolyline(polyline)
            },
            tag = polylineType
        )
    }
}