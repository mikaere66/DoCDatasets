package com.michaelrmossman.docdatasets.util

import com.michaelrmossman.docdatasets.R

const val DOC_BASE_PATH  = "{feature}/FeatureServer/{layer}/query"
const val DOC_BASE_URL   = "https://services1.arcgis.com/3JjYDyG3oajxU6HO/arcgis/rest/services/"
const val DOC_COUNT_ONLY = "returnCountOnly"
const val DOC_DATA_TYPE  = "application/json"
const val DOC_FEATURE    = "feature"
const val DOC_FORMAT     = "f"
const val DOC_FORMAT_VAL = "geojson"
const val DOC_GEOMETRY   = "returnGeometry"
const val DOC_LAYER_VAL  = "layer"
const val DOC_LIMIT_VAL  = "resultRecordCount"
const val DOC_OBJECT_ID  = "OBJECTID"
const val DOC_OFFSET_VAL = "resultOffset"
const val DOC_OUT_S_R    = "outSR"
const val DOC_OUTFIELDS  = "outFields"
const val DOC_OUT_F_VAL  = "*"
const val DOC_PATH_VAL   = "resId"
const val DOC_WHERE      = "where"
const val DOC_WHERE_VAL  = "1=1"

const val DEBUG_SHOW_ADDITIONAL_MESSAGES = false
const val DEBUG_SHOW_RETROFIT_MESSAGES = false

const val FULL_DATE_FORMAT = "EEE, dd MMM yyyy"
/* e.g. 2017-04-01T00:00:00Z */
const val JOINED_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'"

val JSON_ZIP_RAW_RES_ID = R.raw.json_all_files_no_geometry

const val MAP_MARKER_BACKGROUND_ALPHA = 0.6F
/* Midway betwn Otorohanga/Tokoroa */
const val MAP_MIDDLE_NI_LAT = -38.20
const val MAP_MIDDLE_NI_LON = 175.57
/* Just west/Tadmore: Upper Sth Is */
const val MAP_MIDDLE_NZ_LAT = -41.45
const val MAP_MIDDLE_NZ_LON = 172.45
/* Tekapo Canal: Mid South Island */
const val MAP_MIDDLE_SI_LAT = -44.07
const val MAP_MIDDLE_SI_LON = 170.27
/* Alpha calculated at 33% of 256 */
const val MAP_POLYGON_FILL_ALPHA = 85.333333333F
/* Polygon default stroke is 10F */
const val MAP_POLYGON_STROKE_NARROW = 2F
const val MAP_POLYGON_STROKE_THICK  = 8F
/* Polyline default width is 10F */
const val MAP_POLYLINE_WIDTH = 12F
/* Higher num gives "closer" zoom */
const val MAP_ZOOM_IN_OUT = 5.5F
const val MAP_ZOOM_NTH_IS = 6.45F
const val MAP_ZOOM_PADDING = 100
const val MAP_ZOOM_SINGLE  = 10.5F
const val MAP_ZOOM_STH_IS = 6.0F