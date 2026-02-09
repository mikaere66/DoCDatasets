package com.michaelrmossman.docdatasets.ui.features

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.Dp
import com.michaelrmossman.docdatasets.R
import com.michaelrmossman.docdatasets.data.DataSetEntity
import com.michaelrmossman.docdatasets.model.Feature

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeatureItemPage(
    dataset: DataSetEntity,
    feature: Feature,
    paddingHorizontal: Dp,
    paddingValues: PaddingValues,
    paddingVertical: Dp,
    modifier: Modifier = Modifier
) {
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = dimensionResource(R.dimen.card_elevation)
        ),
        modifier = modifier
            .fillMaxSize()
            .padding(paddingValues),
        shape = RoundedCornerShape(
            size = dimensionResource(R.dimen.card_corner_shape)
        )
    ) {

        FeatureItemPageContent(
            dataset = dataset,
            feature = feature,
            modifier = Modifier.fillMaxSize(),
            paddingHorizontal = paddingHorizontal,
            paddingVertical = paddingVertical
        )
    }
}