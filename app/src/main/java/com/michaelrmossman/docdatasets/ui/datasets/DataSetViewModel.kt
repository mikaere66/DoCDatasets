package com.michaelrmossman.docdatasets.ui.datasets

import android.app.Application
import android.util.Log
import androidx.compose.foundation.lazy.LazyListState
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.michaelrmossman.docdatasets.DoCDataSetsApplication
import com.michaelrmossman.docdatasets.data.DataSetEntity
import com.michaelrmossman.docdatasets.data.DataSetRepository
import com.michaelrmossman.docdatasets.data.NetworkRepository
import com.michaelrmossman.docdatasets.data.SettingRepository
import com.michaelrmossman.docdatasets.database.SETTING_TIP_LONG_DESCR
import com.michaelrmossman.docdatasets.model.Feature
import com.michaelrmossman.docdatasets.network.UrlResponse
import com.michaelrmossman.docdatasets.objects.DataTypeKt
import com.michaelrmossman.docdatasets.state.DataSetListState
import com.michaelrmossman.docdatasets.util.FeatureCollectionDataSerializer
import com.michaelrmossman.docdatasets.util.FileUtils.getDataSetFilename
import com.michaelrmossman.docdatasets.util.IntegerUtils.getNumResults
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.io.File
import java.io.IOException

class DataSetViewModel(
    private val datasetsApplication: Application,
    private val datasetSelection : DataSetEntity?,
    private val datasetRepository: DataSetRepository,
    private val existingListState: DataSetListState?,
    private val networkRepository: NetworkRepository,
    private val settingRepository: SettingRepository
) : AndroidViewModel(datasetsApplication), DefaultLifecycleObserver {

    override fun onDestroy(owner: LifecycleOwner) {
        if (datasetSelection != null) {
            datasetRepository.resetListState(
                dataset = null
            )
        }
        super.onDestroy(owner)
    }

    companion object {
        const val TAG = "DataSetViewModel"
    }

    val datasetLazyListState = LazyListState()

    val datasetListState: StateFlow<DataSetListState>
        get() = datasetRepository.datasetListState

    fun getCollectionCount(dataset: DataSetEntity) {
        val callback: (UrlResponse) -> Unit = { response ->
            when (val resultCode = response.responseCode) {
                200 -> {
                    response.collection?.properties?.count?.let { count ->
                        if (count > 0) {
                            setDataSetCount(
                                datasetId = dataset.id,
                                xCount = count
                            )
                        }
                    }
                }
                else -> Log.d(TAG,resultCode.toString())
            }
        }

        try {
            networkRepository.getCollectionCount(
                callback = callback,
                resId = dataset.resId,
                resLayer = dataset.resLayer
            )

        } catch (exception: IOException) {
            datasetRepository.setStateError()
            Log.d(TAG,exception.toString())
        }
    }

    fun getFeatureCollection(
        dataset: DataSetEntity,
        startFrom: Int? = null,
        updateDataset: Boolean = false
    ) {
        /* Only relevant to user action "Update list from Cloud"
           from either DataSetMapScreen or FeatureListScreen */
        if (updateDataset) {
            resetListState(dataset,true)
        }

        viewModelScope.launch {
            val numResults = getNumResults(
                settingRepository.settingNumResults.first()
            )
            val callback: (UrlResponse) -> Unit = { response ->
                val collection = response.collection
                when (val resultCode = response.responseCode) {
                    200 -> when (collection) {
                        null -> updateEmptyDataSet()
                        else -> updateDataSetList(
                            dataset = dataset,
                            features = collection.features,
                            /* Note elvis operator */
                            startFrom = startFrom ?: 0,
                            updateDataset = updateDataset
                        )
                    }
                    else -> updateUiState(
                        message = response.responseMessage,
                        resultCode = resultCode
                    )
                }
            }

            try {
                networkRepository.getFeatureCollection(
                    callback = callback,
                    context = datasetsApplication.applicationContext,
                    filename = getDataSetFilename(
                        resId = dataset.resId,
                        resLayer = dataset.resLayer
                    ),
                    limit = numResults,
                    offset = datasetListState.value.features.size,
                    resId = dataset.resId,
                    resLayer = dataset.resLayer,
                    updateDataset = updateDataset
                )

            } catch (exception: IOException) {
                datasetRepository.setStateError()
                Log.d(TAG,exception.toString())
            }
        }
    }

    fun getGeometryByObjectId(
        dataset: DataSetEntity,
        feature: Feature,
        singleMap: Boolean = true
    ) {
        if (singleMap) {
            datasetRepository.resetFeatureState()
        }

        viewModelScope.launch {
            val callback: (UrlResponse) -> Unit = { response ->
                val collection = response.collection
                when (val resultCode = response.responseCode) {
                    200 -> when (collection) {
                        null -> updateEmptyDataSet()
                        else -> updateFeature(
                            feature = collection.features.first(),
                            singleMap = singleMap
                        )
                    }
                    else -> updateUiState(
                        message = response.responseMessage,
                        resultCode = resultCode
                    )
                }
            }

            try {
                networkRepository.getGeometryByObjectId(
                    callback = callback,
                    objectId = feature.properties.objectId,
                    outFields = dataset.outFields,
                    resId = dataset.resId,
                    resLayer = dataset.resLayer
                )

            } catch (exception: IOException) {
                datasetRepository.setStateError()
                Log.d(TAG,exception.toString())
            }
        }
    }

    fun getGeometryMultiMap(dataset: DataSetEntity) {
        if (datasetListState.value.filter != null) {
            datasetListState.value.features.forEachIndexed { index, feature ->
                getGeometryByObjectId(
                    dataset = dataset,
                    feature = feature,
                    singleMap = (index == 0) /* resetFeatureState() on first */
                )
            }
        }
    }

    fun updateFeature(
        feature: Feature,
        singleMap: Boolean
    ) {
        when (singleMap) {
            true -> datasetRepository.setStateFeature(feature)
            else -> datasetRepository.setStateMultiMap(feature)
        }
    }

    init {
        viewModelScope.launch {
            when (existingListState) {
                null -> {
                    datasetRepository.setStateInit()
                    /* setCurrentDataType called
                       directly from DatasetsApp */
                    datasetSelection?.let { dataset ->
                        setCurrentDataSet(dataset)
                    }
                }
                else -> {
                    datasetRepository.setStateExisting(
                        existingListState = existingListState
                    )
                }
            }
        }
    }

    fun resetListState(
        dataset: DataSetEntity,
        updateDataset: Boolean
    ) = datasetRepository.resetListState(
        dataset = dataset,
        updateDataset = updateDataset
    )

    // fun resetUiState() = datasetRepository.resetUiState()

    fun setCurrentDataSet(dataset: DataSetEntity) {
        resetListState(
            dataset = dataset,
            updateDataset = (dataset.xCount == 0)
        )

        val context = datasetsApplication.applicationContext
        val filename = getDataSetFilename(
            resId = dataset.resId,
            resLayer = dataset.resLayer
        )
        when (
            dataset.xCount != 0
            &&
            File(context.filesDir,filename).exists()
            &&
            File(context.filesDir,filename).length() > 0
        ) {
            true -> {
                val serializer =
                    FeatureCollectionDataSerializer(context)
                serializer.deserializeFeatureCollectionFromFile(
                    filename = filename
                )?.let { collection ->
                    updateDataSetList(
                        dataset = dataset,
                        features = collection.features,
                        startFrom = collection.features.size
                    )
                }
                ?: getFeatureCollection(dataset,updateDataset = true)
            }
            else -> getFeatureCollection(dataset,updateDataset = true)
        }
    }

    fun setCurrentDataType(dataType: DataTypeKt) {
        viewModelScope.launch {
            datasetRepository.setStateDataType(dataType)
        }
    }

    private fun setDataSetCount(
        datasetId: Int,
        xCount: Int
    ) {
        viewModelScope.launch {
            datasetRepository.setDataSetCount(
                datasetId = datasetId,
                xCount = xCount
            )
        }
    }

    fun setFilterListBy(filterListBy: String?) {
        viewModelScope.launch {
            /* An update to a database Upsert would return -1L */
            if (datasetRepository.setFilterListBy(filterListBy) != 0L) {
                datasetListState.value.dataset?.let { dataset ->
                    setCurrentDataSet(
                        dataset = dataset
                    )
                }
            }
        }
    }

    val settingDisablePopups =
        settingRepository.settingDisablePopups.asLiveData()
    val settingSatelliteView =
        settingRepository.settingSatelliteView.asLiveData()

    suspend fun showLongPressForFullDescr(): Boolean {
        /* An upsert would return a new rowId of 1L or greater, but
           if row already exists, the return value would be -1L */
        return settingRepository.saveSetting(
            settingId = SETTING_TIP_LONG_DESCR,
            setting = 1
        ) > 0L
    }

    private fun updateDataSetList( // 200 result code: success
        dataset: DataSetEntity,
        features: List<Feature>,
        startFrom: Int,
        updateDataset: Boolean = false
    ) {
        viewModelScope.launch {
            datasetRepository.setStateSuccess(
                dataset = dataset,
                features = features,
                startFrom = startFrom
            )

            if (updateDataset) {
                getCollectionCount(dataset = dataset)
            }
        }
    }

    private fun updateEmptyDataSet() { // 200, but empty file
        datasetRepository.setStateEmpty()
    }

    private fun updateUiState( // non-200 result code (error)
        resultCode: Int,
        message: String? = null
    ) {
        datasetRepository.setStateUi(
            message = message,
            resultCode = resultCode
        )
    }

    class Factory(
        private val dataset: DataSetEntity? = null,
        private val listState: DataSetListState? = null
    ) : ViewModelProvider.Factory {
        val application = DoCDataSetsApplication.Companion.instance
        val datasetRepository = application.container.datasetRepository
        val networkRepository = application.container.networkRepository
        val settingRepository = application.container.settingRepository
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return DataSetViewModel(
                application,
                dataset,
                datasetRepository,
                listState,
                networkRepository,
                settingRepository
            ) as T
        }
    }
}