package com.michaelrmossman.docdatasets.navigation

import androidx.navigation3.runtime.NavKey
import com.michaelrmossman.docdatasets.data.DataSetEntity
import com.michaelrmossman.docdatasets.model.Feature
import com.michaelrmossman.docdatasets.objects.DataTypeKt
import com.michaelrmossman.docdatasets.state.DataSetListState
import kotlinx.serialization.Serializable

sealed interface CurrentScreen: NavKey {

    @Serializable
    data object AppSettings: CurrentScreen

    @Serializable
    data class DatasetDetails(
        val dataset: DataSetEntity,
        val datasetListState: DataSetListState,
        val initialPage: Int
    ) : CurrentScreen

    @Serializable
    data class DataSetList(
        val dataType: DataTypeKt
    ) : CurrentScreen

    @Serializable
    data class DatasetMap(
        val dataset: DataSetEntity,
        val feature: Feature
    ) : CurrentScreen

    @Serializable
    data class DatasetMultiMap(
        val dataset: DataSetEntity
    ) : CurrentScreen

    @Serializable
    data object DataTypeList: CurrentScreen

    @Serializable
    data class FeatureDetails(
        val dataset: DataSetEntity,
        val datasetListState: DataSetListState,
        val initialPage: Int
    ) : CurrentScreen

    @Serializable
    data class FeatureList(
        val dataset: DataSetEntity,
        val datasetListState: DataSetListState,
        val initialPage: Int
    ) : CurrentScreen
}