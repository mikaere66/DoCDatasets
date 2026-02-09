package com.michaelrmossman.docdatasets.state

import com.michaelrmossman.docdatasets.data.DataSetEntity
import com.michaelrmossman.docdatasets.model.Feature
import com.michaelrmossman.docdatasets.objects.DataTypeKt
import kotlinx.serialization.Serializable

@Serializable
data class DataSetListState(

    val canDLMore: Boolean = false,

    val dataset  : DataSetEntity? = null,
    val datasets : List<DataSetEntity> = emptyList(),

    val dataType : DataTypeKt = DataTypeKt(),
    val dataTypes: List<DataTypeKt> = emptyList(),

    // val feature  : Feature? = null,
    val features : List<Feature> = emptyList(),
    val ftsDloaded: Int = 0,
    val ftsMultMap: List<Feature> = emptyList(), /* Temp */

    val filter   : String? = null,
    val filterBy : List<String?> = emptyList(),

    val message  : String? = null,

    val uiState  : DataSetUiState = DataSetUiState.Loading
)