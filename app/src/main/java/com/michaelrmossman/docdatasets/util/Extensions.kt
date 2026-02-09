package com.michaelrmossman.docdatasets.util

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.fromHtml
import androidx.compose.ui.text.style.TextDecoration
import com.google.android.gms.maps.model.LatLng
import com.michaelrmossman.docdatasets.DoCDataSetsApplication.Companion.instance
import com.michaelrmossman.docdatasets.R
import com.michaelrmossman.docdatasets.data.DataSetEntity
import com.michaelrmossman.docdatasets.enum.DataSet
import com.michaelrmossman.docdatasets.enum.DataSetFilter
import com.michaelrmossman.docdatasets.enum.PolygonType
import com.michaelrmossman.docdatasets.enum.PolylineType
import com.michaelrmossman.docdatasets.model.Feature
import com.michaelrmossman.docdatasets.model.Geometry
import com.michaelrmossman.docdatasets.util.TextUtils.getAathOrWaroRestrictionText
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Extension functions used throughout the app
 */

fun Feature.distinctElement(dataset: DataSetEntity): String? {
    /* Preceding number represents num of unique elements returned by filter */
    return when (DataSetFilter.valueOf(dataset.dataSet)) {
        /* DataSetFilter is a subset of DataSet: only the "filterable" lists */
        /*  (7) */ DataSetFilter.AATH,DataSetFilter.WARO -> {
            this.properties.restriction1
        }
        /*  (7) */ DataSetFilter.CMS_AAZ,DataSetFilter.CMS_VMS -> {
            this.properties.cmsArea
        }
        /* (18) */ DataSetFilter.Chamois,DataSetFilter.Fallow,
                   DataSetFilter.Ferrets,DataSetFilter.Goats,
                   DataSetFilter.Hare,   DataSetFilter.Possum,
                   DataSetFilter.RedDeer,DataSetFilter.Stoat,
                   DataSetFilter.Tahr -> this.properties.office

        /*  (4) */ DataSetFilter.Covenants -> this.properties.type

        /* (18) */ DataSetFilter.Campsites,DataSetFilter.Huts -> {
            this.properties.region
        }
        /* (35) */ DataSetFilter.Freedom -> this.properties.operationsDistrict

        /* (13) */ DataSetFilter.MTB_Locales, DataSetFilter.MTB_Routes,
                   DataSetFilter.Walk_Locales,DataSetFilter.Walk_Routes -> {
            this.properties.difficulty
        }
        /*  (6) */ DataSetFilter.Offices -> this.properties.type

        /* (11) */ DataSetFilter.OpDistricts -> this.properties.regionName

        /* (11) */ DataSetFilter.PCLand -> this.properties.section

        /*  (9) */ DataSetFilter.Roads,DataSetFilter.Tracks -> {
            this.properties.subObjectType
        }
        /* (79) */ DataSetFilter.Ecology -> this.properties.ecologicalReg

        /* (15) */ DataSetFilter.NMFF -> this.properties.docRegion

        /* (59) */ DataSetFilter.Hunting -> this.properties.permitArea

        /* (50) */ DataSetFilter.TFI -> this.properties.commonName1
    }
}

fun Feature.mapMarkerSnippet(dataSet: String): String {
    return this.outFields(DataSet.valueOf(dataSet)).second ?: String()
}

fun Feature.mapMarkerTitle(dataSet: String): String {
    return this.outFields(DataSet.valueOf(dataSet)).first ?: String()
}

private fun Feature.outFields(dataSet: DataSet): Pair<String?, String?> {
    /* All datasets have two [outfields], except the
       Distrib Of datasets, plus Freedom Camping */
    return with (this.properties) {
        when (dataSet) {
            DataSet.AATH -> blockName to getAathOrWaroRestrictionText(
                restriction1,restriction2
            )
            DataSet.Chamois,DataSet.Fallow,DataSet.Ferrets,
            DataSet.Goats,  DataSet.Hare,  DataSet.Possum,
            DataSet.RedDeer,DataSet.Stoat, DataSet.Tahr -> {
                office to instance.getString(
                    R.string.map_snippet_abundance,
                    abundance,abundReliability
                )
            }
            DataSet.CMS_VMS      -> cmsArea to vmzName
            DataSet.Covenants    -> name0 to instance.getString(
                R.string.map_snippet_hectares,recordedArea
            )
            DataSet.Campsites    -> name to place
            DataSet.CMS_AAZ      -> cmsArea to cmsAirZone
            DataSet.Freedom      -> name0 to instance.getString(
                R.string.map_snippet_format,siteCategoryType,address
            )
            DataSet.Huts         -> name to place
            DataSet.MarineRes    -> name0 to instance.getString(
                R.string.map_snippet_hectares,recordedArea
            )
            DataSet.MTB_Locales,DataSet.MTB_Routes -> name to difficulty
            DataSet.Offices      -> name0 to physicalAddressLine1
            DataSet.OpDistricts  -> districtName to regionName
            DataSet.OpRegions    -> regionName to regionCode
            DataSet.PCLand       -> name0 to instance.getString(
                R.string.map_snippet_hectares,recordedArea
            )
            DataSet.Roads,DataSet.Tracks -> techObjectName to subObjectType
            DataSet.Sanct        -> name0 to section
            DataSet.Walk_Locales,DataSet.Walk_Routes -> name0 to difficulty
            DataSet.Ecology      -> ecologicalDist to ecologicalReg
            DataSet.Kea          -> keaPresence to scroungeInfluenced
            DataSet.NMFF          -> catchment to docDistrict
            DataSet.Hunting      -> huntBlockName to permitArea
            DataSet.TFI          -> commonName1 to umbrellaCat
            DataSet.WARO         -> blockName to status
        }
    }
}

fun Feature.sortBy(dataset: DataSetEntity): String? {
    return when (DataSet.valueOf(dataset.dataSet)) {
        DataSet.AATH,DataSet.WARO -> this.properties.blockName
        DataSet.CMS_AAZ,DataSet.CMS_VMS -> this.properties.cmsArea
        DataSet.Chamois,DataSet.Fallow,DataSet.Ferrets,
        DataSet.Goats,  DataSet.Hare,  DataSet.Possum,
        DataSet.RedDeer,DataSet.Stoat, DataSet.Tahr -> {
            this.properties.office
        }
        DataSet.Covenants -> this.properties.name0
        DataSet.Campsites,DataSet.Huts -> {
            /* Allow for Māori special characters */
            this.properties.name?.replaceMacrons()
        }
        DataSet.Freedom -> this.properties.name0
        DataSet.MarineRes -> this.properties.name0
        DataSet.MTB_Locales, DataSet.MTB_Routes,
        DataSet.Walk_Locales,DataSet.Walk_Routes -> {
            this.properties.name
        }
        DataSet.Offices -> this.properties.name0
        DataSet.OpDistricts -> this.properties.districtName
        DataSet.OpRegions -> this.properties.regionName
        DataSet.PCLand -> this.properties.name0
        DataSet.Roads,DataSet.Tracks -> this.properties.techObjectName
        DataSet.Sanct -> this.properties.name0
        DataSet.Ecology -> this.properties.ecologicalDist
        DataSet.Kea -> this.properties.objectId.toString() //
        DataSet.NMFF -> this.properties.catchment // was docRegion
        DataSet.Hunting -> this.properties.huntBlockName
        DataSet.TFI -> this.properties.commonName1 // was scientificName1
    }
}

fun Geometry.coordsListPolygon(
    polyType: PolygonType
) : List<List<List<LatLng>>> {
    when (polyType) {
        PolygonType.Polygon, PolygonType.Ring -> {
            val coordsList = mutableListOf<List<LatLng>>()
            (this as Geometry.Polygon).coordinates.forEach { coords ->
                coordsList.add(coords.map { doubles ->
                    doubles.getLatLng()
                })
            }
            return listOf(coordsList)
        }
        PolygonType.MultiPolygon -> {
            val coordsList = mutableListOf<List<List<LatLng>>>()
            (this as Geometry.MultiPolygon).coordinates.forEach { coords ->
                val innerList = mutableListOf<List<LatLng>>()
                coords.forEach { innerCoords ->
                    innerList.add(innerCoords.map { doubles ->
                        doubles.getLatLng()
                    })
                }
                coordsList.add(innerList)
            }
            return coordsList
        }
    }
}

fun Geometry.coordsListPolyline(
    polyType: PolylineType
) : List<List<LatLng>> {
    when (polyType) {
        PolylineType.LineString -> {
            return listOf(
                (this as Geometry.LineString).coordinates.map { doubles ->
                    doubles.getLatLng()
                }
            )
        }
        PolylineType.MultiLine -> {
            val coordsList = mutableListOf<List<LatLng>>()
            (this as Geometry.MultiLine).coordinates.forEach { coords ->
                coordsList.add(coords.map { doubles ->
                    doubles.getLatLng()
                })
            }
            return coordsList
        }
    }
}

fun Int.formatWithCommas(): String {
    return NumberFormat.getNumberInstance(
        Locale.getDefault()
    ).format(this)
}

fun List<Double>.getLatLng(): LatLng {
    /* Latitude/longitude are reversed in Json.
       Some coordinates may incl. elevation */
    val latitude = when (this.size) {
        // e.g. Latitude: -41.5276939
        2 -> when (val first = this.first()) {
            in 0.0..Double.MAX_VALUE -> this.last()
            else -> first
        }
        else -> this[1]
    }
    val longitude = when (this.size) {
        // e.g. Longitude: 171.9375505
        2 -> when (val last = this.last()) {
            in 0.0..Double.MAX_VALUE -> last
            else -> this.first()
        }
        else -> this.first()
    }
    return LatLng(latitude,longitude)
}

fun Long.parseMillisToDate(): String {
    return SimpleDateFormat(
        FULL_DATE_FORMAT, Locale.getDefault()
    ).format(Date(this))
}

@Composable
fun String.fromHtml(): AnnotatedString {
    return AnnotatedString.Companion.fromHtml(
        htmlString = this,
        linkStyles = TextLinkStyles(
            style = SpanStyle(
                textDecoration = TextDecoration.Underline,
                color = MaterialTheme.colorScheme.primary
            )
        )
    )
}

/* ignoreCase does not seem to work */
fun String.replaceMacrons(): String {
    /* Locale("mi","NZ") deprecated */
    return this
        .replace("Ā","A")
        .replace("Ē","E")
        .replace("Ī","I")
        .replace("Ō","O")
        .replace("Ū","U")
}
