package com.michaelrmossman.docdatasets.ui.features

import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.Dp
import com.michaelrmossman.docdatasets.R
import com.michaelrmossman.docdatasets.data.DataSetEntity
import com.michaelrmossman.docdatasets.model.Feature

@Composable
fun FeatureListItem(
    // dataset: DataSetEntity,
    feature: Feature,
    onClickFeature: (Feature) -> Unit,
    onLongClickFeature: (Feature) -> Unit,
    paddingHorizontal: Dp,
    paddingVertical: Dp,
    /* Modifier used by all [Feature] text items */
    modifier: Modifier = Modifier
) {
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = dimensionResource(
                R.dimen.card_elevation
            )
        ),
        modifier = Modifier.combinedClickable(
            onClick = { onClickFeature(feature) },
            onLongClick = { onLongClickFeature(feature) }
        ),
        // onClick = { onClickFeature(feature) },
        shape = RoundedCornerShape(
            dimensionResource(R.dimen.card_corner_shape)
        ),
    ) {
        Column(
            modifier = Modifier.padding(
                end = paddingHorizontal,
                start = paddingHorizontal,
                top = paddingVertical
            ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(
                dimensionResource(R.dimen.padding_small)
            )
        ) {
            FeatureListItemContent(
                // dataset = dataset,
                feature = feature,
                fullText = false,
                modifier = modifier
            )
        }
    }
}