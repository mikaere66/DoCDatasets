package com.michaelrmossman.docdatasets.ui.maps

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Polygon
import com.google.android.gms.maps.model.Polyline
import com.google.maps.android.compose.MapEffect
import com.google.maps.android.compose.MapsComposeExperimentalApi
import com.google.maps.android.compose.rememberCameraPositionState
import com.michaelrmossman.docdatasets.R
import com.michaelrmossman.docdatasets.data.DataSetEntity
import com.michaelrmossman.docdatasets.enum.PolygonType
import com.michaelrmossman.docdatasets.enum.PolylineType
import com.michaelrmossman.docdatasets.model.Feature
import com.michaelrmossman.docdatasets.model.Geometry
import com.michaelrmossman.docdatasets.state.DataSetUiState
import com.michaelrmossman.docdatasets.ui.ErrorScreen
import com.michaelrmossman.docdatasets.ui.InvalidScreen
import com.michaelrmossman.docdatasets.ui.LoadingScreen
import com.michaelrmossman.docdatasets.ui.NotFoundScreen
import com.michaelrmossman.docdatasets.ui.components.BottomSheetCommon
import com.michaelrmossman.docdatasets.ui.components.DynamicActionMenu
import com.michaelrmossman.docdatasets.ui.components.TwoLineAppBar
import com.michaelrmossman.docdatasets.ui.datasets.DataSetViewModel
import com.michaelrmossman.docdatasets.util.BitmapParameters
import com.michaelrmossman.docdatasets.util.IconColor
import com.michaelrmossman.docdatasets.util.MAP_MARKER_BACKGROUND_ALPHA
import com.michaelrmossman.docdatasets.util.MAP_MIDDLE_NI_LAT
import com.michaelrmossman.docdatasets.util.MAP_MIDDLE_NI_LON
import com.michaelrmossman.docdatasets.util.MAP_MIDDLE_NZ_LAT
import com.michaelrmossman.docdatasets.util.MAP_MIDDLE_NZ_LON
import com.michaelrmossman.docdatasets.util.MAP_MIDDLE_SI_LAT
import com.michaelrmossman.docdatasets.util.MAP_MIDDLE_SI_LON
import com.michaelrmossman.docdatasets.util.MAP_ZOOM_IN_OUT
import com.michaelrmossman.docdatasets.util.MAP_ZOOM_NTH_IS
import com.michaelrmossman.docdatasets.util.MAP_ZOOM_PADDING
import com.michaelrmossman.docdatasets.util.MAP_ZOOM_SINGLE
import com.michaelrmossman.docdatasets.util.MAP_ZOOM_STH_IS
import com.michaelrmossman.docdatasets.util.MapUtils.markerContent
import com.michaelrmossman.docdatasets.util.MapUtils.polygonContent
import com.michaelrmossman.docdatasets.util.MapUtils.polylineContent
import com.michaelrmossman.docdatasets.util.TextUtils.getMapsSubtitle
import com.michaelrmossman.docdatasets.util.TextUtils.getDatasetMapLoadingStringId
import com.michaelrmossman.docdatasets.util.coordsListPolygon
import com.michaelrmossman.docdatasets.util.coordsListPolyline
import com.michaelrmossman.docdatasets.util.getLatLng
import com.michaelrmossman.docdatasets.util.vectorToBitmap

@OptIn(ExperimentalMaterial3Api::class, MapsComposeExperimentalApi::class)
@Composable
fun DataSetMapScreen(
    dataset: DataSetEntity,
    datasetViewModel: DataSetViewModel,
    onClickBackButton: () -> Unit,
    modifier: Modifier = Modifier,
    singleFeature: Feature? = null
) {
    var bottomSheetFeature by remember { mutableStateOf<Feature?>(null) }
    var clearMap by remember { mutableStateOf(false) }
    var clickedPolygon by remember { mutableStateOf<Polygon?>(null) }
    var clickedPolyline by remember { mutableStateOf<Polyline?>(null) }
    val onClickPolygon = { feature: Feature, polygon: Polygon ->
        bottomSheetFeature = feature
        clickedPolygon = polygon
    }
    val onClickPolyline = { feature: Feature, polyline: Polyline ->
        bottomSheetFeature = feature
        clickedPolyline = polyline
    }
    val onDismissRequest = {
        clickedPolyline = null
        bottomSheetFeature = null
    }
    val settingDisablePopups by
        datasetViewModel.settingDisablePopups.observeAsState(initial = 0)
    val settingSatelliteView by
        datasetViewModel.settingSatelliteView.observeAsState(initial = 0)
    val viewState by
        datasetViewModel.datasetListState.collectAsStateWithLifecycle()
    val zoomAllIs = LatLng(MAP_MIDDLE_NZ_LAT,MAP_MIDDLE_NZ_LON)
    var zoomEnabled by remember { mutableStateOf(false) }
    var zoomInOut by remember { mutableStateOf<LatLng?>(null) }
    var zoomLevel by remember { mutableFloatStateOf(MAP_ZOOM_IN_OUT) }
    val zoomNthIs = LatLng(MAP_MIDDLE_NI_LAT,MAP_MIDDLE_NI_LON)
    val zoomSthIs = LatLng(MAP_MIDDLE_SI_LAT,MAP_MIDDLE_SI_LON)

    var masterCount by remember { mutableIntStateOf(0) }
    val markerCoords = mutableListOf<LatLng>()
    val polygonCoords = mutableListOf<List<List<List<LatLng>>>>()
    val polylineCoords = mutableListOf<List<List<LatLng>>>()
    viewState.ftsMultMap.forEach { feature ->
        when (val geometry = feature.geometry) {
            is Geometry.LineString -> {
                val coordsList = geometry.coordsListPolyline(
                    PolylineType.LineString
                )
                polylineCoords.add(coordsList)
            }
            is Geometry.MultiLine -> {
                val coordsList = geometry.coordsListPolyline(
                    PolylineType.MultiLine
                )
                polylineCoords.add(coordsList)
            }
            is Geometry.MultiPolygon -> {
                val coordsList = geometry.coordsListPolygon(
                    polyType = PolygonType.MultiPolygon
                )
                polygonCoords.add(coordsList)
            }
            is Geometry.Point -> {
                markerCoords.add(
                    geometry.coordinates.getLatLng()
                )
            }
            is Geometry.Polygon -> {
                val coordsList = geometry.coordsListPolygon(
                    polyType = PolygonType.Polygon
                )
                polygonCoords.add(coordsList)
            }
            is Geometry.Rings -> {
                val coordsList = geometry.coordsListPolygon(
                    polyType = PolygonType.Polygon
                )
                polygonCoords.add(coordsList)
            }
            else -> { /* Geometry is Nullable */ }
        }
        if (masterCount < viewState.ftsMultMap.size) {
            masterCount = masterCount.plus(1)
        }
    }

    val masterBuilder = LatLngBounds.Builder()
    var zoomCurrent by remember { mutableStateOf(false) }
    val masterList = mutableListOf<LatLng>()
    /* Need at least 2 coordinates for latLng builder */
    val cameraPosition = when (
        markerCoords.isEmpty()
        &&
        polygonCoords.isEmpty()
        &&
        polylineCoords.isEmpty()
    ) {
        true -> zoomAllIs
        else -> {
            masterList.addAll(markerCoords)
            polygonCoords.forEach { outerList ->
                outerList.forEach { innerList ->
                    masterList.addAll(innerList.flatten())
                }
            }
            polylineCoords.forEach { outerList ->
                masterList.addAll(outerList.flatten())
            }
            when (masterList.size) {
                in 0..1 -> zoomAllIs // Just for safety
                else -> {
                    masterList.forEach { latLng ->
                        masterBuilder.include(latLng)
                    }
                    masterBuilder.build().center
                }
            }
        }
    }
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            cameraPosition, MAP_ZOOM_IN_OUT
        )
    }

    /* DynamicActionMenu Items */
    val isEnabled = mutableListOf(
        zoomEnabled,
        zoomEnabled,
        zoomEnabled
    )
    val menuLabels = listOf(
        R.string.menu_zoom_both,
        R.string.menu_zoom_north,
        R.string.menu_zoom_south
    ).map { stringId ->
        stringResource(stringId)
    }.toMutableList()
    val onClickActions = mutableListOf(
        {
            zoomLevel = MAP_ZOOM_IN_OUT
            zoomInOut = zoomAllIs
        },
        {
            zoomLevel = MAP_ZOOM_NTH_IS
            zoomInOut = zoomNthIs
        },
        {
            zoomLevel = MAP_ZOOM_STH_IS
            zoomInOut = zoomSthIs
        }
    )
    /* End DynamicActionMenu
       (see also map content) */

    bottomSheetFeature?.let { feature ->
        if (settingDisablePopups == 0) {
            BottomSheetCommon(
                dataset = dataset,
                feature = feature,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        dimensionResource(R.dimen.padding_list_item)
                    ),
                onDismissRequest = onDismissRequest
            )
        }
    }

    LaunchedEffect(key1 = clickedPolygon) {
        bottomSheetFeature?.let { feature ->
            clickedPolygon?.let { polygon ->
                polygon.tag?.let { tag ->
                    val geometry = feature.geometry
                    val coordsList = geometry?.coordsListPolygon(
                        tag as PolygonType
                    )
                    val builder = LatLngBounds.Builder()
                    coordsList?.forEach { outerList ->
                        outerList.forEach { innerList ->
                            innerList.forEach { latLng ->
                                builder.include(latLng)
                            }
                        }
                    }
                    cameraPositionState.move(
                        CameraUpdateFactory.newLatLngBounds(
                            builder.build(),MAP_ZOOM_PADDING
                        )
                    )
                }
            }
        }
    }

    LaunchedEffect(key1 = clickedPolyline) {
        bottomSheetFeature?.let { feature ->
            clickedPolyline?.let { polyline ->
                polyline.tag?.let { tag ->
                    val geometry = feature.geometry
                    val coordsList = geometry?.coordsListPolyline(
                        tag as PolylineType
                    )
                    val builder = LatLngBounds.Builder()
                    coordsList?.forEach { list ->
                        list.forEach { latLng ->
                            builder.include(latLng)
                        }
                    }
                    cameraPositionState.move(
                        CameraUpdateFactory.newLatLngBounds(
                            builder.build(),MAP_ZOOM_PADDING
                        )
                    )
                }
            }
        }
    }

    LaunchedEffect(
        key1 = zoomCurrent,
        key2 = viewState.uiState
    ) {
        if (
            zoomCurrent
            &&
            viewState.uiState != DataSetUiState.Downloading
            &&
            viewState.uiState != DataSetUiState.Loading
        ) {
            if (masterList.isNotEmpty()) {
                when (masterList.size) {
                    1 -> cameraPositionState.move(
                        CameraUpdateFactory.newLatLngZoom(
                            masterList.first(),MAP_ZOOM_SINGLE
                        )
                    )
                    else -> cameraPositionState.move(
                        CameraUpdateFactory.newLatLngBounds(
                            masterBuilder.build(),MAP_ZOOM_PADDING
                        )
                    )
                }
            }
            zoomCurrent = false
        }
    }

    LaunchedEffect(key1 = zoomInOut) {
        zoomInOut?.let { latLng ->
            cameraPositionState.animate(
                CameraUpdateFactory.newLatLngZoom(
                    latLng,zoomLevel
                )
            )
            zoomInOut = null
        }
    }

    Scaffold(topBar = {
        TwoLineAppBar(
            actions = {
                DynamicActionMenu(
                    isEnabled = isEnabled,
                    menuLabels = menuLabels,
                    onClickActions = onClickActions
                )
            },
            onClickBackButton = onClickBackButton,
            title = stringResource(R.string.app_alias),
            subtitle = getMapsSubtitle(
                dataset = dataset,
                ftsMulti = maxOf(
                    /* Accurate count is inconsistent */
                    masterCount,viewState.ftsMultMap.size
                ),
                ftsSize = viewState.features.size
            )
        )
    }) { contentPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
        ) {

            val content: (@Composable () -> Unit) = {
                DataSetMapContent(
                    cameraPositionState = cameraPositionState,
                    onMapLoaded = {
                        zoomCurrent = true
                        zoomEnabled = true
                    },
                    satelliteView = settingSatelliteView
                ) { /* Content in following lambda */
                    MapEffect(key1 = clearMap) { mapView ->
                        if (clearMap) {
                            mapView.clear()
                            clearMap = false
                        }
                    }
                    val colors = IconColor(
                        iconColor = MaterialTheme.colorScheme.onPrimary,
                        backgroundColor = MaterialTheme.colorScheme.primary.copy(
                            alpha = MAP_MARKER_BACKGROUND_ALPHA
                        ),
                        borderColor = MaterialTheme.colorScheme.primary
                    )
                    /* vectorToBitmap must be called within map scope */
                    val icon = vectorToBitmap(
                        LocalContext.current,
                        BitmapParameters(
                            id = R.drawable.outline_place_24,
                            iconColor = colors.iconColor.toArgb(),
                            backgroundColor = colors.backgroundColor.toArgb()
                        )
                    )
                    var pluralsId = 0
                    val singleItem = (singleFeature != null)
                    if (markerCoords.isNotEmpty()) {
                        pluralsId = R.plurals.menu_zoom_markers
                        markerCoords.forEachIndexed { index, coords ->
                            val lat = coords.latitude
                            val lon = coords.longitude
                            markerContent(
                                dataset,
                                viewState.ftsMultMap[index],
                                lat,
                                lon,
                                icon
                            )
                        }
                    }
                    if (polygonCoords.isNotEmpty()) {
                        pluralsId = R.plurals.menu_zoom_polygons
                        polygonCoords.forEachIndexed { index, coordsList ->
                            polygonContent(
                                coordsList,
                                viewState.ftsMultMap[index],
                                onClickPolygon,
                                PolygonType.MultiPolygon,
                                singleItem
                            )
                        }
                    }
                    if (polylineCoords.isNotEmpty()) {
                        pluralsId = R.plurals.menu_zoom_polylines
                        polylineCoords.forEachIndexed { index, coordsList ->
                            polylineContent(
                                coordsList,
                                viewState.ftsMultMap[index],
                                PolylineType.LineString,
                                onClickPolyline
                            )
                        }
                    }
                    if (
                        masterCount == viewState.ftsMultMap.lastIndex
                        &&
                        masterList.isNotEmpty()
                        &&
                        pluralsId != 0
                    ) {
                        isEnabled.add(1,zoomEnabled)
                        menuLabels.add(1,pluralStringResource(
                            pluralsId,
                            masterCount
                        ))
                        onClickActions.add(1,{ zoomCurrent = true })
                    }
                }
            }

            when (viewState.uiState) {
                is DataSetUiState.Downloading,
                        DataSetUiState.GettingMore -> LoadingScreen(
                    modifier = modifier,
                    stringId = getDatasetMapLoadingStringId(
                        fromFile = false
                    )
                )
                is DataSetUiState.Error -> ErrorScreen(
                    errorMessage = viewState.message,
                    modifier = modifier,
                    retryAction = {
                        when (singleFeature) {
                            null -> datasetViewModel.getGeometryMultiMap(
                                dataset = dataset
                            )
                            else -> datasetViewModel.getGeometryByObjectId(
                                dataset = dataset,
                                feature = singleFeature
                            )
                        }
                    }
                )
                is DataSetUiState.Invalid -> InvalidScreen(
                    modifier = modifier
                )
                is DataSetUiState.Loading -> LoadingScreen(
                    modifier = modifier,
                    stringId = getDatasetMapLoadingStringId(
                        fromFile = true
                    )
                )
                is DataSetUiState.NotFound -> NotFoundScreen(
                    dataset = dataset,
                    modifier = modifier,
                )
                is DataSetUiState.None,
                        DataSetUiState.Success -> {
                    content()
                }
                is DataSetUiState.Unavailable -> ErrorScreen(
                    errorMessage = viewState.message,
                    modifier = modifier /* No retry button */
                )
            }
        }
    }
}