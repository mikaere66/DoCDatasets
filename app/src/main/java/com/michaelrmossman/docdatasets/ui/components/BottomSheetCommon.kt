package com.michaelrmossman.docdatasets.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.michaelrmossman.docdatasets.R
import com.michaelrmossman.docdatasets.data.DataSetEntity
import com.michaelrmossman.docdatasets.model.Feature
import com.michaelrmossman.docdatasets.ui.features.FeatureItemPageContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetCommon(
    dataset: DataSetEntity,
    feature: Feature,
    onDismissRequest: () -> Unit,
    /* Modifier used by all [Feature] text items */
    modifier: Modifier = Modifier
) {
    val bottomSheetState = rememberModalBottomSheetState()
    val paddingHorizontal = dimensionResource(R.dimen.padding_horizontal)
    val paddingVertical = dimensionResource(R.dimen.padding_vertical)
    val spacingVertical = dimensionResource(R.dimen.spacing_vertical)

    ModalBottomSheet(
        onDismissRequest = { onDismissRequest() },
        sheetState = bottomSheetState
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    horizontal = paddingHorizontal
                ),
            verticalArrangement = Arrangement.spacedBy(spacingVertical)
        ) {

            FeatureItemPageContent(
                dataset = dataset,
                feature = feature,
                modifier = modifier,
                paddingHorizontal = paddingHorizontal,
                paddingVertical = paddingVertical
            )
        }
    }
}