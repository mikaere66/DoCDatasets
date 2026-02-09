package com.michaelrmossman.docdatasets.data

import android.content.Context
import com.michaelrmossman.docdatasets.database.DoCDataSetsDatabase
import com.michaelrmossman.docdatasets.model.Geometry
import com.michaelrmossman.docdatasets.network.DoCDataSetsApiService
import com.michaelrmossman.docdatasets.util.DEBUG_SHOW_RETROFIT_MESSAGES
import com.michaelrmossman.docdatasets.util.DOC_BASE_URL
import com.michaelrmossman.docdatasets.util.DOC_DATA_TYPE
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import java.util.concurrent.TimeUnit

interface AppContainer {

    val docDataSetsDb: DoCDataSetsDatabase

    val datasetRepository: DataSetRepository

    val networkRepository: NetworkRepository

    val settingRepository: SettingRepository
}

class DefaultAppContainer(context: Context): AppContainer {

    private val appModule = SerializersModule {
        polymorphic(Geometry::class) {
            subclass(Geometry.LineString::class)
            subclass(Geometry.MultiLine::class)
            subclass(Geometry.MultiPolygon::class)
            subclass(Geometry.Point::class)
            subclass(Geometry.Polygon::class)
            subclass(Geometry.Rings::class)
        }
    }
    private val json = Json {
        serializersModule = appModule
        ignoreUnknownKeys = false
    }
    @Suppress("KotlinConstantConditions")
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        when (DEBUG_SHOW_RETROFIT_MESSAGES) {
            true -> setLevel(HttpLoggingInterceptor.Level.HEADERS) // was BODY
            else -> setLevel(HttpLoggingInterceptor.Level.NONE)
        }
    }
    /* The above solution shows logcat messages similar to the previous
       Retrofit method, using setLogLevel(RestAdapter.LogLevel.FULL) */
    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .connectTimeout(30,TimeUnit.SECONDS)
        .readTimeout(30,TimeUnit.SECONDS)
        .writeTimeout(30,TimeUnit.SECONDS)
        .build()
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(
            json.asConverterFactory(
                DOC_DATA_TYPE.toMediaType()
            )
        )
        .baseUrl(DOC_BASE_URL)
        .client(client)
        .build()
    private val retrofitService: DoCDataSetsApiService by lazy {
        retrofit.create(DoCDataSetsApiService::class.java)
    }

    override val docDataSetsDb = DoCDataSetsDatabase.getDatabase(context)

    override val datasetRepository: DataSetRepository by lazy {
        DataSetRepository(
            docDataSetsDb.dataSetDao(),
            docDataSetsDb.dataTypeDao(),
            docDataSetsDb.settingDao()
        )
    }

    override val networkRepository: NetworkRepository by lazy {
        NetworkRepository(retrofitService)
    }

    override val settingRepository: SettingRepository by lazy {
        SettingRepository(docDataSetsDb.settingDao())
    }
}