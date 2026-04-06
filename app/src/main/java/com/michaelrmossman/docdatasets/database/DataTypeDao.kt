package com.michaelrmossman.docdatasets.database

import androidx.room.Dao
import androidx.room.RawQuery
import androidx.sqlite.db.SimpleSQLiteQuery
import com.michaelrmossman.docdatasets.objects.DataTypeKt

@Dao
interface DataTypeDao {

    @RawQuery
    suspend fun getDataTypesKt(
        query: SimpleSQLiteQuery
    ) : List<DataTypeKt>
}