package com.michaelrmossman.docdatasets.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.michaelrmossman.docdatasets.database.COLUMN_NAME_DATA_TITLE
import com.michaelrmossman.docdatasets.database.COLUMN_NAME_DESCRIPT
import com.michaelrmossman.docdatasets.database.COLUMN_NAME_ITEM_ID
import com.michaelrmossman.docdatasets.database.TABLE_NAME_DATA_TYPE

@Entity(tableName = TABLE_NAME_DATA_TYPE)
data class DataTypeEntity(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = COLUMN_NAME_ITEM_ID)
    val id: Int,

    @ColumnInfo(name = COLUMN_NAME_DATA_TITLE)
    val title: String,

    @ColumnInfo(name = COLUMN_NAME_DESCRIPT)
    val description: String
)