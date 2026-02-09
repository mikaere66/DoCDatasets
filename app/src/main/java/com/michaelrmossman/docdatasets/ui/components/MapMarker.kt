package com.michaelrmossman.docdatasets.ui.components

import androidx.compose.runtime.Composable
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.maps.android.compose.MapsComposeExperimentalApi
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberUpdatedMarkerState
import com.michaelrmossman.docdatasets.objects.MarkerProperties

/**
 * Shows a single [MarkerProperties] map marker
 */
@OptIn(MapsComposeExperimentalApi::class)
@Composable
fun MapMarker(
    placeIcon: BitmapDescriptor,
    onMarkerClick: (Marker) -> Boolean,
    properties: MarkerProperties
) {
    Marker(
        icon = placeIcon,
        onClick = { marker ->
            onMarkerClick(marker)
            false
        },
        snippet = properties.snippet,
        state = rememberUpdatedMarkerState(
            position = LatLng(
                properties.lat,
                properties.lon
            )
        ),
        title = properties.title
    )
}