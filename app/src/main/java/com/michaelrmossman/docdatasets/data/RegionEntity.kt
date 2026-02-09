package com.michaelrmossman.docdatasets.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.michaelrmossman.docdatasets.database.COLUMN_NAME_ITEM_ID
import com.michaelrmossman.docdatasets.database.COLUMN_NAME_REGION
import com.michaelrmossman.docdatasets.database.TABLE_NAME_REGION

/* To sort DoC Campsites and Huts */
@Entity(tableName = TABLE_NAME_REGION)
data class RegionEntity(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = COLUMN_NAME_ITEM_ID)
    val id: Int,

    @ColumnInfo(name = COLUMN_NAME_REGION)
    val region: String
)