package com.michaelrmossman.docdatasets.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.michaelrmossman.docdatasets.R
import com.michaelrmossman.docdatasets.database.COLUMN_NAME_FILTER_BY
import com.michaelrmossman.docdatasets.database.COLUMN_NAME_DATA_SET
import com.michaelrmossman.docdatasets.database.TABLE_NAME_FILTER_BY

@Entity(tableName = TABLE_NAME_FILTER_BY)
data class FilterEntity( // Potentially, same qty as [DataSetEntity]

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = COLUMN_NAME_DATA_SET)
    val dataSet: String,

    @ColumnInfo(name = COLUMN_NAME_FILTER_BY)
    val filterBy: String?
)