package com.michaelrmossman.docdatasets.util

import androidx.annotation.DimenRes
import androidx.annotation.PluralsRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.withLink
import androidx.compose.ui.unit.sp
import com.michaelrmossman.docdatasets.DoCDataSetsApplication.Companion.instance
import com.michaelrmossman.docdatasets.R
import com.michaelrmossman.docdatasets.data.DataSetEntity
import com.michaelrmossman.docdatasets.objects.DataTypeKt
import java.util.Comparator

/**
 * Text utility functions used throughout the app
 */
object TextUtils {

    @Composable
    @ReadOnlyComposable
    fun fontDimensionResource(@DimenRes id: Int) =
        dimensionResource(id = id).value.sp

    fun getAathOrWaroRestrictionText(
        restriction1: String?, restriction2: String?
    ) : String {
        // restriction2?.let { android.util.Log.d("HEY",it) }
        return instance.resources.getQuantityString(
            /* Asterix indicates exception. Retrofit calls
               for three outfields, but only two shown */
            R.plurals.feature_aath_restriction,
            restriction1?.split("excluding")?.size?.minus(1) ?: 0,
            when (restriction2?.trim()?.length) {
                0    -> restriction1
                else -> restriction2
            }
        )
    }

    fun getDatasetMapLoadingStringId(fromFile: Boolean): Int {
        return when (fromFile) {
            true -> R.string.loading_from_file
            else -> R.string.loading_from_doc
        }
    }

    @Composable
    fun getFeaturesSubtitle(
        dataset : DataSetEntity,
        ftsSize: Int,
        ftsDloaded: Int,
        xCount: Int
    ) : String {
        val docPrefix = "DOC " /* Note trailing space */
        val lengthMax = 30 /* Not including docPrefix */
        val title = when (dataset.title.length) {
            in 0..lengthMax -> dataset.title
            else -> when (
                dataset.title.startsWith(docPrefix)
            ) {
                true -> dataset.title.substring(
                    docPrefix.length,lengthMax
                )
                else -> dataset.title.substring(
                    0,lengthMax
                )
            }
        }
        return when (ftsDloaded) {
            xCount -> stringResource(
                R.string.dataset_subtitle,
                title.trim(),
                ftsSize.formatWithCommas()
            )
            else -> stringResource(
                R.string.dataset_subtitle_filtered,
                title.trim(),
                ftsSize.formatWithCommas(),
                xCount.formatWithCommas()
            )
        }
    }

    @Composable
    fun getIsActiveText(
        isActive: Int?
    ) : String {
        return when (isActive) {
            null -> getUndefinedText()
            else -> stringResource(
                when (isActive) {
                    0 -> R.string.is_active_no
                    else -> R.string.is_active_yes
                }
            )
        }
    }

    /* Map feature-list is ALWAYS filtered */
    @Composable
    fun getMapsSubtitle(
        dataset : DataSetEntity,
        ftsMulti: Int,
        ftsSize: Int
    ) : String {
        return stringResource(
            R.string.dataset_subtitle_filtered,
            dataset.title,
            ftsMulti.formatWithCommas(),
            ftsSize.formatWithCommas()
        )
    }

    @Composable
    fun getPropertyText(
        property: Any,
        @StringRes stringId: Int
    ) : AnnotatedString {
        return stringResource(
            R.string.feature_format,
            stringResource(stringId),
            property
        ).fromHtml()
    }

    @Composable
    fun getUndefinedText(): String =
        stringResource(R.string.text_undefined)

    @Composable
    fun getWebAnnotatedString(
        contentUrl: String,
        linkText: String
    ) : AnnotatedString {
        return buildAnnotatedString {
            append(linkText)
            append(" ") /* Note space */
            val uriHandler = LocalUriHandler.current
            val link = LinkAnnotation.Url(
                contentUrl,
                TextLinkStyles(
                    SpanStyle(
                        textDecoration = TextDecoration.Underline,
                        color = Color.Blue
                    )
                )
            ) { url ->
                uriHandler.openUri((url as LinkAnnotation.Url).url)
            }
            withLink(link) { append(contentUrl) }
        }
    }
}