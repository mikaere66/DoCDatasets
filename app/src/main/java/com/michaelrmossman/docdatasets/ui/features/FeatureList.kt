package com.michaelrmossman.docdatasets.ui.features

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.Dp
import com.michaelrmossman.docdatasets.R
import com.michaelrmossman.docdatasets.data.DataSetEntity
import com.michaelrmossman.docdatasets.model.Feature
import com.michaelrmossman.docdatasets.state.DataSetListState
import com.michaelrmossman.docdatasets.state.DataSetUiState
import com.michaelrmossman.docdatasets.ui.components.ButtonWithIcon
import com.michaelrmossman.docdatasets.util.ModifierUtils.downloading
import com.michaelrmossman.docdatasets.util.ResourceUtils.downloadDrawableIds
import com.michaelrmossman.docdatasets.util.ResourceUtils.downloadStringIds

@Composable
fun FeatureList(
    contentPadding: PaddingValues,
    // dataset: DataSetEntity,
    datasetLazyListState: LazyListState,
    onClickDownloadMore: () -> Unit,
    onClickFeature: (Feature) -> Unit,
    onLongClickFeature: (Feature) -> Unit,
    paddingHorizontal: Dp,
    paddingVertical: Dp,
    viewState: DataSetListState,
    /* Modifier used by all [Feature] text items */
    modifier: Modifier = Modifier
) {
    val spacingVertical = dimensionResource(R.dimen.spacing_vertical)

    LazyColumn(
        contentPadding = contentPadding,
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = paddingVertical),
        state = datasetLazyListState,
        verticalArrangement = Arrangement.spacedBy(
            spacingVertical
        )
    ) {
        itemsIndexed(
            items = viewState.features
        ) { _, feature ->

            FeatureListItem(
                // dataset = dataset,
                feature = feature,
                modifier = modifier,
                onClickFeature = onClickFeature,
                onLongClickFeature = onLongClickFeature,
                paddingHorizontal = paddingHorizontal,
                paddingVertical = paddingVertical
            )
        }

        val isDownloading =
            viewState.uiState is DataSetUiState.GettingMore
        item(
            key = Int.MAX_VALUE
        ) {
            ButtonWithIcon(
                drawableId = when (isDownloading) {
                    true -> downloadDrawableIds.first
                    else -> when (viewState.canDLMore) {
                        true -> downloadDrawableIds.second
                        else -> downloadDrawableIds.third
                    }
                },
                isEnabled = (
                    viewState.canDLMore
                    &&
                    !isDownloading
                ),
                /* Refer custom modifier in ModifierUtils */
                modifier = Modifier.downloading(isDownloading),
                onClickButton = onClickDownloadMore,
                stringId = when (isDownloading) {
                    true -> downloadStringIds.first
                    else -> when (viewState.canDLMore) {
                        true -> downloadStringIds.second
                        else -> downloadStringIds.third
                    }
                }
            )
        }
    }
}