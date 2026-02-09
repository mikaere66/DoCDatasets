package com.michaelrmossman.docdatasets.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.michaelrmossman.docdatasets.database.COLUMN_NAME_DATA_SET
import com.michaelrmossman.docdatasets.database.COLUMN_NAME_DATA_TYPE
import com.michaelrmossman.docdatasets.database.COLUMN_NAME_OUT_FIELDS
import com.michaelrmossman.docdatasets.database.COLUMN_NAME_ITEM_ID
import com.michaelrmossman.docdatasets.database.TABLE_NAME_DATA_SET
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = TABLE_NAME_DATA_SET) // 34
data class DataSetEntity(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = COLUMN_NAME_ITEM_ID)
    val id: Int,

    @ColumnInfo
    val title: String,

    @ColumnInfo(name = COLUMN_NAME_DATA_SET)
    val dataSet: String,

    @ColumnInfo(name = COLUMN_NAME_DATA_TYPE)
    val dataType: Int,

    @ColumnInfo
    val description: String,

    @ColumnInfo
    val extras: String?,

    /* Whether or not it's okay to show multiple (filtered)
       items on one map. False means the app would crash */
    @ColumnInfo
    val multiMap: Boolean,

    /* When requesting Geometry, get these 2 or 3 data
       fields as well, for use with Maps title/snippet */
    @ColumnInfo(name = COLUMN_NAME_OUT_FIELDS)
    val outFields: String,

    @ColumnInfo
    val resId: String,

    @ColumnInfo
    val resLayer: Int,

    @ColumnInfo
    val webUrl1: String,

    @ColumnInfo
    val webUrl2: String,

    @ColumnInfo
    val xCount: Int
)