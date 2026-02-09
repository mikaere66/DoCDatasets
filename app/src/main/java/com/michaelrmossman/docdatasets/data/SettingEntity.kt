package com.michaelrmossman.docdatasets.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.michaelrmossman.docdatasets.database.COLUMN_NAME_SETTING
import com.michaelrmossman.docdatasets.database.COLUMN_NAME_SETTING_ID
import com.michaelrmossman.docdatasets.database.TABLE_NAME_SETTING

@Entity(tableName = TABLE_NAME_SETTING) // Potentially 4
data class SettingEntity(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = COLUMN_NAME_SETTING_ID)
    val settingId: String,

    @ColumnInfo(name = COLUMN_NAME_SETTING)
    val setting: Int
)