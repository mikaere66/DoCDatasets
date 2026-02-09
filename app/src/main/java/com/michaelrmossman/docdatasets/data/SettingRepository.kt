package com.michaelrmossman.docdatasets.data

import com.michaelrmossman.docdatasets.database.SettingDao
import com.michaelrmossman.docdatasets.database.SETTING_DISABLE_POPUPS
import com.michaelrmossman.docdatasets.database.SETTING_GET_NUM_RESULTS
import com.michaelrmossman.docdatasets.database.SETTING_SATELLITE_VIEW
import kotlinx.coroutines.flow.Flow

class SettingRepository(
    private val settingDao: SettingDao
) {

    /* Note diff entity / table name */

    suspend fun deleteAllFilterItems(): Int =
        settingDao.deleteAllFilterItems()

    fun getFilterItemsCount(): Flow<Int> =
        settingDao.getFilterItemsCount()

    fun getSettingsNotDefault(): Flow<Boolean> =
        settingDao.getSettingItemsExist()

    suspend fun resetAllSettings(): Int {
        settingDao.deleteAllFilterItems() // Reset

//        val settings = SettingEntity.getSettings()
//        return settingDao.updateSettings(settings)
        return settingDao.deleteAllSettingItems()
    }

    /* Actual setting_table functions */

    val settingDisablePopups: Flow<Int> =
        settingDao.getSettingByIdFlow(
            settingId = SETTING_DISABLE_POPUPS
        )

    /* Not actual size, but index of integer-array */
    val settingNumResults: Flow<Int> =
        settingDao.getSettingByIdFlow(
            settingId = SETTING_GET_NUM_RESULTS
        )

    val settingSatelliteView: Flow<Int> =
        settingDao.getSettingByIdFlow(
            settingId = SETTING_SATELLITE_VIEW
        )

    suspend fun saveSetting(
        settingId: String, setting: Int
    ) : Long {
        val settingEntity = SettingEntity(
            settingId = settingId,
            setting = setting
        )
        /* Note upsert : in case new Setting
           is added after production/install */
        return settingDao.upsertSetting(settingEntity)
    }
}