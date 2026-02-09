package com.michaelrmossman.docdatasets.ui.features

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.Dp
import com.michaelrmossman.docdatasets.R
import com.michaelrmossman.docdatasets.data.DataSetEntity
import com.michaelrmossman.docdatasets.enum.DataSetExtras
import com.michaelrmossman.docdatasets.model.Feature
import com.michaelrmossman.docdatasets.util.TextUtils.getWebAnnotatedString

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeatureItemPageContent(
    dataset: DataSetEntity,
    feature: Feature,
    paddingHorizontal: Dp,
    paddingVertical: Dp,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .padding(
                horizontal = paddingHorizontal,
                vertical = paddingVertical
            )
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(
            dimensionResource(R.dimen.padding_small)
        )
    ) {

        FeatureListItemContent(
            feature = feature,
            fullText = true,
            modifier = modifier
        )

        /* Only relevant to Freedom Camping
           plus, the DataSetExtras shown below */
        dataset.extras?.let { extras ->
            val extrasText = when (extras.startsWith("https")) {
                true -> {
                    val stringId = when (
                        DataSetExtras.valueOf(dataset.dataSet)
                    ) {
                        DataSetExtras.AATH    -> R.string.dataset_aath_url
                        DataSetExtras.CMS_VMS -> R.string.dataset_cms1_url
                        DataSetExtras.CMS_AAZ -> R.string.dataset_cms2_url
                        DataSetExtras.TFI     -> R.string.dataset_tfi_url
                        DataSetExtras.WARO    -> R.string.dataset_waro_url
                    }
                    getWebAnnotatedString(
                        contentUrl = extras,
                        linkText = stringResource(stringId)
                    )
                }
                else -> { /* Freedom Camping */
                    buildAnnotatedString { append(extras) }
                }
            }
            Text(
                text = extrasText,
                modifier = modifier
            )
        }

        FeatureItemContent(
            dataset = dataset,
            feature = feature,
            modifier = modifier
        )
    }
}