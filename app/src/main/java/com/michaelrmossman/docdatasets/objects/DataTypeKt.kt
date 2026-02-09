package com.michaelrmossman.docdatasets.objects

import kotlinx.serialization.Serializable

@Serializable
data class DataTypeKt(

    val id: Int = 0,

    val title: String = String(),

    val description: String = String(),

    val itemCount: Int = 0
)