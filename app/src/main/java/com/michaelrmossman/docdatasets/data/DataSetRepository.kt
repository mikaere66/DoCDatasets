package com.michaelrmossman.docdatasets.data

import com.michaelrmossman.docdatasets.DoCDataSetsApplication.Companion.instance
import com.michaelrmossman.docdatasets.R
import com.michaelrmossman.docdatasets.database.DataSetDao
import com.michaelrmossman.docdatasets.database.DataTypeDao
import com.michaelrmossman.docdatasets.database.DbHelpers.getDataTypeQuery
import com.michaelrmossman.docdatasets.database.SettingDao
import com.michaelrmossman.docdatasets.enum.DataSetFilter
import com.michaelrmossman.docdatasets.model.Feature
import com.michaelrmossman.docdatasets.objects.DataTypeKt
import com.michaelrmossman.docdatasets.state.DataSetListState
import com.michaelrmossman.docdatasets.state.DataSetUiState
import com.michaelrmossman.docdatasets.util.distinctElement
import com.michaelrmossman.docdatasets.util.replaceMacrons
import com.michaelrmossman.docdatasets.util.sortBy
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class DataSetRepository(
    private val dataSetDao : DataSetDao,
    private val dataTypeDao: DataTypeDao,
    private val settingDao : SettingDao
) {
    private val _datasetListState by lazy {
        MutableStateFlow(DataSetListState())
    }
    val datasetListState: StateFlow<DataSetListState>
        get() = _datasetListState

    private suspend fun getDataTypesKt(): List<DataTypeKt> {
        return dataTypeDao.getDataTypesKt(getDataTypeQuery())
    }

    fun resetFeatureState() {
        _datasetListState.update { currentState ->
            currentState.copy(
                ftsMultMap = emptyList(),
                uiState = DataSetUiState.Downloading
            )
        }
    }

    fun resetListState(
        dataset: DataSetEntity?,
        updateDataset: Boolean = false
    ) {
        _datasetListState.update { currentState ->
            currentState.copy(
                dataset = dataset,
                dataTypes = currentState.dataTypes,
                features = emptyList(),
                ftsDloaded = 0,
                message = null,
                uiState = when (updateDataset) {
                    true -> DataSetUiState.Downloading
                    else -> DataSetUiState.Loading
                }
            )
        }
    }

//    fun resetUiState() {
//        _datasetListState.update { currentState ->
//            currentState.copy(
//                uiState = DataSetUiState.None
//            )
//        }
//    }

    suspend fun setDataSetCount(
        datasetId: Int,
        xCount: Int
    ) {
        if (dataSetDao.setDataSetCount(
            id = datasetId,
            xCount = xCount
        ) > 0) {
            _datasetListState.update { currentState ->
                currentState.copy(
                    dataset = currentState.dataset?.copy(
                        xCount = xCount
                    ),
                    datasets = currentState.datasets.map { dataset ->
                        when (dataset.id) {
                            datasetId -> dataset.copy(
                                xCount = xCount
                            )
                            else -> dataset
                        }
                    }
                )
            }
        }
    }

    suspend fun setFilterListBy(
        filterListBy: String?
    ) : Long = datasetListState.value.dataset?.dataSet?.let { dataSet ->
        settingDao.upsertFilterBy(
            FilterEntity(
                dataSet = dataSet,
                filterBy = filterListBy
            )
        )
    } ?: 0L

    /* Update State */

    suspend fun setStateDataType(dataType: DataTypeKt) {
        _datasetListState.update { currentState ->
            currentState.copy(
                datasets = dataSetDao.getDataSetsByDataTypeId(
                    dataTypeId = dataType.id
                ),
                dataType = dataType
            )
        }
    }

    fun setStateEmpty() {
        _datasetListState.update { currentState ->
            currentState.copy(
                uiState = DataSetUiState.Invalid
            )
        }
    }

    fun setStateError() {
        _datasetListState.update { currentState ->
            currentState.copy(
                uiState = DataSetUiState.Error
            )
        }
    }

    fun setStateExisting(
        existingListState: DataSetListState
    ) {
        _datasetListState.update { currentState ->
            currentState.copy(
                datasets = existingListState.datasets
            )
        }
    }

    fun setStateFeature(feature: Feature) {
        _datasetListState.update { currentState ->
            currentState.copy(
                ftsMultMap = listOf(feature),
                uiState = DataSetUiState.Success
            )
        }
    }

    fun setStateMultiMap(feature: Feature) {
        _datasetListState.update { currentState ->
            currentState.copy(
                ftsMultMap = currentState.ftsMultMap.plus(feature)
            )
        }
    }

    suspend fun setStateInit() {
        if (datasetListState.value.dataTypes.isEmpty()) {
            _datasetListState.update { currentState ->
                currentState.copy(
                    dataTypes = getDataTypesKt()
                )
            }
        }
    }

    suspend fun setStateSuccess(
        dataset: DataSetEntity,
        features: List<Feature>,
        startFrom: Int
    ) {
        val filterDatasetBy = settingDao.getSettingById(
            dataSet = dataset.dataSet
        )
        /* If user has opted to filter list, then do so */
        val featuresFiltered = when (filterDatasetBy) {
            null -> features
            else -> features.filter { feature ->
                (filterDatasetBy
                ==
                feature.distinctElement(dataset))
            }
        }
        /* Regardless of filter, sort by logical element */
        val featuresSorted = featuresFiltered.sortedBy { feature ->
            feature.sortBy(dataset)
        }.filter { feature ->
            /* e.g. 3 items in TFI have NO name|details */
            feature.sortBy(dataset) != null
        }
        /* Again, get the distinct elements to filter
           relevant list, for the "Filter by" dialog */
        val filterByList = when (
            DataSetFilter.entries.map { entry ->
                entry.name
            }.contains(dataset.dataSet)
        ) {
            true -> features.distinctBy { feature ->
                feature.distinctElement(dataset)
            }.mapNotNull { feature ->
                feature.distinctElement(dataset)
            }.sortedBy { element ->
                /* Sort alphabetically, allowing
                   for Māori special characters */
                element.replaceMacrons()
            }.toMutableList().apply {
                add(0,instance.getString(
                    R.string.filter_show_all
                ))
            }
            else -> emptyList()
        }

        _datasetListState.update { currentState ->
            currentState.copy(
                features = when (startFrom) {
                    0 -> featuresSorted
                    else -> currentState.features.plus(
                        featuresSorted
                    )
                },
                ftsDloaded = when (startFrom) {
                    0 -> features.size
                    else -> currentState.ftsDloaded.plus(
                        features.size
                    )
                },
                filter = filterDatasetBy,
                filterBy = filterByList,
                uiState = DataSetUiState.Success
            )
        }

        /* Now that "list" & "count" are
           updated, update "canDownload" */
        _datasetListState.update { currentState ->
            currentState.copy(
                canDLMore = (
                    currentState.ftsDloaded
                    <
                    dataset.xCount
                )
            )
        }
    }

    fun setStateUi(
        message: String?,
        resultCode: Int
    ) {
        message?.let { msg -> println(msg) }

        _datasetListState.update { currentState ->
            currentState.copy(
                message = message,
                uiState = when (resultCode) {
                    202  -> DataSetUiState.Unavailable
                    404  -> DataSetUiState.NotFound
                    else -> DataSetUiState.Error
                }
            )
        }
    }
}