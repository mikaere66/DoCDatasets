package com.michaelrmossman.docdatasets.model

import kotlinx.serialization.Serializable

@Serializable
data class CollectionProperties(

    val count: Int? = null,
    val exceededTransferLimit: Boolean? = null
)