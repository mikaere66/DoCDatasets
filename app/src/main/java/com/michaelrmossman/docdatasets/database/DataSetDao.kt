package com.michaelrmossman.docdatasets.database

import androidx.room.Dao
import androidx.room.Query
import com.michaelrmossman.docdatasets.data.DataSetEntity

@Dao
interface DataSetDao {

    @Query("""
        SELECT * FROM $TABLE_NAME_DATA_SET
        WHERE $COLUMN_NAME_DATA_TYPE = :dataTypeId
    """)
    suspend fun getDataSetsByDataTypeId(
        dataTypeId: Int
    ) : List<DataSetEntity>

    @Query("""
        UPDATE $TABLE_NAME_DATA_SET
        SET $COLUMN_NAME_X_COUNT = :xCount
        WHERE $COLUMN_NAME_ITEM_ID = :id
    """)
    suspend fun setDataSetCount(
        id: Int, xCount: Int
    ) : Int
}