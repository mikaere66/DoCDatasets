package com.michaelrmossman.docdatasets.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.RawQuery
import androidx.room.Update
import androidx.sqlite.db.SimpleSQLiteQuery
import com.michaelrmossman.docdatasets.objects.DataTypeKt
import kotlinx.coroutines.flow.Flow

@Dao
interface DataTypeDao {

    @RawQuery
    suspend fun getDataTypesKt(
        query: SimpleSQLiteQuery
    ) : List<DataTypeKt>
}