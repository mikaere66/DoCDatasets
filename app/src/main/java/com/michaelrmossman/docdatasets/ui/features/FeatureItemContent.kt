package com.michaelrmossman.docdatasets.ui.features

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.michaelrmossman.docdatasets.R
import com.michaelrmossman.docdatasets.data.DataSetEntity
import com.michaelrmossman.docdatasets.enum.DataSetImages
import com.michaelrmossman.docdatasets.enum.PropertyType
import com.michaelrmossman.docdatasets.model.Feature
import com.michaelrmossman.docdatasets.ui.components.ItemImage
import com.michaelrmossman.docdatasets.util.TextUtils.getWebAnnotatedString
import com.michaelrmossman.docdatasets.util.TextUtils.getPropertyText
import com.michaelrmossman.docdatasets.util.parseMillisToDate

@Composable
fun FeatureItemContent(
    dataset: DataSetEntity,
    feature: Feature,
    modifier: Modifier = Modifier
) {
    /* Refer to EOF in Properties::class.java for iterator() */
    for ((stringId, pair) in feature.properties.iterator()) {
        pair.first?.let { property ->
            if (property.toString().trim().isNotBlank()) {
                var image = false
                var webUrl = false
                val propertyText: Any? = when (pair.second) {
                    PropertyType.Image -> {
                        image = true
                        property
                    }
                    PropertyType.Integer -> {
                        try {
                            /* Allow for default objectId */
                            when (property as Int) {
                                0 -> null
                                else -> property
                            }
                        } catch (exception: ClassCastException) {
                            exception.message?.let { message ->
                                android.util.Log.e(
                                    "FeatureItemContent",
                                    message
                                )
                            }
                            property
                        }
                    }
                    PropertyType.Long -> {
                        try {
                            /* Long types represent dates */
                            (property as Long).parseMillisToDate()
                        } catch (exception: ClassCastException) {
                            exception.message?.let { message ->
                                android.util.Log.e(
                                    "FeatureItemContent",
                                    message
                                )
                            }
                            property
                        }
                    }
                    PropertyType.WebUrl -> {
                        webUrl = true
                        property
                    }
                    else -> property
                }
                propertyText?.let { prop ->
                    when (image) {
                        true -> ItemImage(
                            contentDescrStringId = when (
                                DataSetImages.valueOf(dataset.dataSet)
                            ) {
                                DataSetImages.Campsites -> {
                                    R.string.image_campsite_desc
                                }
                                DataSetImages.Huts -> {
                                    R.string.image_hut_desc
                                }
                                DataSetImages.MTB_Locales,
                                DataSetImages.Walk_Locales -> {
                                    R.string.image_track_desc
                                }
                                DataSetImages.MTB_Routes,
                                DataSetImages.Walk_Routes -> {
                                    R.string.image_route_desc
                                }
                            },
                            imageUrl = prop.toString()
                        )
                        else -> Text(
                            modifier = modifier,
                            text = when (webUrl) {
                                true -> getWebAnnotatedString(
                                    contentUrl = prop.toString(),
                                    linkText = stringResource(stringId)
                                )
                                else -> getPropertyText(
                                    property = prop,
                                    stringId = stringId
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}