package com.michaelrmossman.docdatasets.database

import androidx.sqlite.db.SimpleSQLiteQuery

object DbHelpers {

    fun getDataTypeQuery(): SimpleSQLiteQuery {
        val sb = StringBuilder()

        sb.append("SELECT")
        sb.append(" ")
        sb.append("T.$COLUMN_NAME_ITEM_ID,")
        sb.append("T.$COLUMN_NAME_DATA_TITLE,")
        sb.append("T.$COLUMN_NAME_DESCRIPT,")
        sb.append(" ")
        sb.append("(SELECT COUNT(*) FROM $TABLE_NAME_DATA_SET AS S")
        sb.append(" ") // Note surrounding brackets ▲ ▼
        sb.append("WHERE S.$COLUMN_NAME_DATA_TYPE = T.$COLUMN_NAME_ITEM_ID)")
        sb.append(" ")
        sb.append("AS itemCount")
        sb.append(" ")
        sb.append("FROM $TABLE_NAME_DATA_TYPE AS T")

        // android.util.Log.d("HEY",sb.toString())
        return SimpleSQLiteQuery(sb.toString())
    }
}