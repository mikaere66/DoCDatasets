package com.michaelrmossman.docdatasets.ui.settings

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.michaelrmossman.docdatasets.DoCDataSetsApplication
import com.michaelrmossman.docdatasets.data.DataSetRepository
import com.michaelrmossman.docdatasets.data.SettingRepository
import com.michaelrmossman.docdatasets.database.SETTING_DISABLE_POPUPS
import com.michaelrmossman.docdatasets.database.SETTING_GET_NUM_RESULTS
import com.michaelrmossman.docdatasets.database.SETTING_SATELLITE_VIEW
import com.michaelrmossman.docdatasets.state.SettingUiState
import com.michaelrmossman.docdatasets.util.DEBUG_SHOW_ADDITIONAL_MESSAGES
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SettingViewModel(
    // private val datasetRepository: DataSetRepository,
    private val settingRepository: SettingRepository
) : ViewModel() {

    companion object {
        private const val TAG = "SettingViewModel"
    }

    private val _settingUiState by lazy { MutableStateFlow(SettingUiState()) }
    val settingUiState: StateFlow<SettingUiState> = _settingUiState

    /* Live Data */

    val disablePopups: LiveData<Int> =
        settingRepository.settingDisablePopups.asLiveData()

    val filterCount: LiveData<Int> =
        settingRepository.getFilterItemsCount().asLiveData()

    val resultsSize: LiveData<Int> =
        settingRepository.settingNumResults.asLiveData()

    val satelliteView: LiveData<Int> =
        settingRepository.settingSatelliteView.asLiveData()

    val settingsNotDefault: LiveData<Boolean> =
        settingRepository.getSettingsNotDefault().asLiveData()

    /* Restore Defaults */

    fun clearFilterItems() {
        viewModelScope.launch {
            _settingUiState.update { currentState ->
                currentState.copy(
                    filterItemsDeleted =
                        settingRepository.deleteAllFilterItems()
                )
            }
        }
    }

    fun resetAllSettings() {
        viewModelScope.launch {
            val result = settingRepository.resetAllSettings()
            if (DEBUG_SHOW_ADDITIONAL_MESSAGES) {
                Log.d(TAG,"All settings ($result)")
            }
        }
    }

    fun resetFilterState() {
        _settingUiState.update { currentState ->
            currentState.copy(
                filterItemsDeleted = 0
            )
        }
    }

    /* Save Settings */

    fun saveSetting(settingId: String, setting: Int) {
        viewModelScope.launch {
            val result = settingRepository.saveSetting(
                settingId = settingId,
                setting   = setting
            )
            if (DEBUG_SHOW_ADDITIONAL_MESSAGES) {
                Log.d(TAG,"$settingId: $setting ($result)")
            }
        }
    }

    fun setDisablePopups(disable: Int) {
        saveSetting(SETTING_DISABLE_POPUPS,disable)
    }

    /* Not actual size, but index of integer-array */
    fun setResultsSize(index: Int) {
        saveSetting(SETTING_GET_NUM_RESULTS,index)
    }

    fun setSatelliteView(satellite: Int) {
        saveSetting(SETTING_SATELLITE_VIEW,satellite)
    }

    class Factory() : ViewModelProvider.Factory {
        val application = DoCDataSetsApplication.Companion.instance
        // val datasetRepository = application.container.datasetRepository
        val settingRepository = application.container.settingRepository
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return SettingViewModel(
                // datasetRepository,
                settingRepository
            ) as T
        }
    }
}