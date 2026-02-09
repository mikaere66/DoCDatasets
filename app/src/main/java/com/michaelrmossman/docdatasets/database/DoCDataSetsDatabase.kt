package com.michaelrmossman.docdatasets.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.michaelrmossman.docdatasets.R
import com.michaelrmossman.docdatasets.data.DataSetEntity
import com.michaelrmossman.docdatasets.data.DataTypeEntity
import com.michaelrmossman.docdatasets.data.FilterEntity
import com.michaelrmossman.docdatasets.data.RegionEntity
import com.michaelrmossman.docdatasets.data.SettingEntity
import com.michaelrmossman.docdatasets.util.FileUtils.unzipRawToFilesDir
import com.michaelrmossman.docdatasets.util.JSON_ZIP_RAW_RES_ID
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
        DataSetEntity::class,
        DataTypeEntity::class,
        FilterEntity::class,
        RegionEntity::class,
        SettingEntity::class
    ],
    exportSchema = EXPORT_SCHEMA,
    version = DATABASE_VERSION
)
abstract class DoCDataSetsDatabase: RoomDatabase() {

    abstract fun dataSetDao() : DataSetDao
    abstract fun dataTypeDao(): DataTypeDao
    abstract fun settingDao() : SettingDao

    companion object {
        @Volatile
        private var instance: DoCDataSetsDatabase? = null

        fun getDatabase(context: Context): DoCDataSetsDatabase {
            return instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    DoCDataSetsDatabase::class.java,
                    DATABASE_FILE_NAME
                )
                .createFromAsset(DATABASE_IMPORT_NAME)
                //.fallbackToDestructiveMigration(false) // TODO
                .addCallback(object: Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        // Unzip the .geojson files on the I/O thread
                        CoroutineScope(Dispatchers.IO).launch {
                            unzipRawToFilesDir(context,JSON_ZIP_RAW_RES_ID)
                            // Populate the Settings table w/defaults
//                            instance?.settingDao()?.insertSettings(
//                                settings = SettingEntity.getSettings()
//                            )
                        }
                    }
                })
                .build()
                .also { instance = it}
            }
        }
    }
}