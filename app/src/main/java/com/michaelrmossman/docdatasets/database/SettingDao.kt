package com.michaelrmossman.docdatasets.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.michaelrmossman.docdatasets.data.FilterEntity
import com.michaelrmossman.docdatasets.data.SettingEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SettingDao {

    /* Note diff entity / table name */

    @Query("DELETE FROM $TABLE_NAME_FILTER_BY")
    suspend fun deleteAllFilterItems(): Int

    @Query("""
        SELECT $COLUMN_NAME_FILTER_BY
        FROM $TABLE_NAME_FILTER_BY
        WHERE $COLUMN_NAME_DATA_SET = :dataSet
    """)
    suspend fun getSettingById(dataSet: String): String?

    @Query("""
        SELECT COUNT(*)
        FROM $TABLE_NAME_FILTER_BY
    """)
    fun getFilterItemsCount(): Flow<Int>

    /* Actual setting_table functions */

    @Query("DELETE FROM $TABLE_NAME_SETTING")
    suspend fun deleteAllSettingItems(): Int

    @Query("""
        SELECT setting FROM $TABLE_NAME_SETTING
        WHERE settingId = :settingId
    """)
    fun getSettingByIdFlow(settingId: String): Flow<Int>

//    @Query("SELECT COUNT(*) FROM $TABLE_NAME_SETTING")
    @Query("SELECT EXISTS (SELECT 1 FROM $TABLE_NAME_SETTING)")
    fun getSettingItemsExist(): Flow<Boolean>

//    @Insert // For new database creation
//    suspend fun insertSettings(settings: List<SettingEntity>)

//    @Update // For "restore app defaults"
//    suspend fun updateSettings(settings: List<SettingEntity>): Int

    @Update // For single Setting update
    suspend fun updateSetting(setting: SettingEntity): Int

    @Upsert /* Note diff entity/table */
    suspend fun upsertFilterBy(setting: FilterEntity): Long

    /* Note upsert : in case new Setting
       is added after production/install */
    @Upsert // For single Setting update
    suspend fun upsertSetting(setting: SettingEntity): Long
}