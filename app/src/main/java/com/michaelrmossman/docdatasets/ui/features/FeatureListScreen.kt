package com.michaelrmossman.docdatasets.ui.features

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.michaelrmossman.docdatasets.R
import com.michaelrmossman.docdatasets.data.DataSetEntity
import com.michaelrmossman.docdatasets.enum.DataSetFilter
import com.michaelrmossman.docdatasets.model.Feature
import com.michaelrmossman.docdatasets.state.DataSetListState
import com.michaelrmossman.docdatasets.state.DataSetUiState
import com.michaelrmossman.docdatasets.ui.ErrorScreen
import com.michaelrmossman.docdatasets.ui.InvalidScreen
import com.michaelrmossman.docdatasets.ui.LoadingScreen
import com.michaelrmossman.docdatasets.ui.NotFoundScreen
import com.michaelrmossman.docdatasets.ui.components.BottomSheetDescription
import com.michaelrmossman.docdatasets.ui.components.DynamicActionMenu
import com.michaelrmossman.docdatasets.ui.components.TwoLineAppBar
import com.michaelrmossman.docdatasets.ui.datasets.DataSetViewModel
import com.michaelrmossman.docdatasets.util.DialogUtils.FilterByDialog
import com.michaelrmossman.docdatasets.util.TextUtils.getFeaturesSubtitle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeatureListScreen(
    datasetViewModel: DataSetViewModel,
    onClickBackButton: () -> Unit,
    onClickFeature: (DataSetEntity, DataSetListState, Int) -> Unit,
    onLongClickFeature: (DataSetEntity, Feature) -> Unit,
    onClickMapButton: (DataSetEntity) -> Unit,
    modifier: Modifier = Modifier
) {
    val paddingHorizontal = dimensionResource(R.dimen.padding_horizontal)
    val paddingVertical = dimensionResource(R.dimen.padding_vertical)
    var showBottomSheetDescription by remember { mutableStateOf(false) }
    var showFilterByDialog by remember { mutableStateOf(false) }
    val viewState by
        datasetViewModel.datasetListState.collectAsStateWithLifecycle()

    /* For this screen, subtitle will need updating if xCount
       changes, so we get dataset directly from viewState */
    viewState.dataset?.let { dataset ->
        if (showBottomSheetDescription) {
            BottomSheetDescription(
                dataset = dataset,
                onDismissRequest = { showBottomSheetDescription = false }
            )
        }
        if (showFilterByDialog) {
            val selection = viewState.filterBy.indexOf(
                viewState.filter
            )
            val headers = stringArrayResource(R.array.filter_by_headers)
            val headerText= headers[
                DataSetFilter.valueOf(dataset.dataSet).ordinal
            ]
            FilterByDialog(
                currentSelection = when (selection) {
                    in -1..0 -> 0
                    else -> selection
                },
                filterByListItems = viewState.filterBy,
                headerText = headerText,
                onClickConfirm = { filterListBy ->
                    showFilterByDialog = false
                    datasetViewModel.setFilterListBy(
                        filterListBy
                    )
                },
                onClickDismiss = { showFilterByDialog = false },
                stringId = R.string.filter_header_text
            )
        }

        val enabledCommon =
            (viewState.uiState != DataSetUiState.Downloading
            &&
            viewState.uiState != DataSetUiState.GettingMore
            &&
            viewState.uiState != DataSetUiState.Unavailable)
        /* DynamicActionMenu Items */
        val isEnabled = mutableListOf(
            true,
            enabledCommon,
            (
                viewState.filter != null
                &&
                dataset.multiMap
                &&
                enabledCommon
            )
        )
        val menuLabels = listOf(
            R.string.menu_dataset_desc,
            R.string.menu_update_list,
            when (dataset.multiMap) {
                true -> R.string.menu_map_filtered
                else -> R.string.menu_map_not_avail
            }
        ).map { stringId ->
            stringResource(stringId)
        }.toMutableList()
        val onClickActions = mutableListOf(
            { showBottomSheetDescription = true },
            {
                datasetViewModel.getFeatureCollection(
                    dataset = dataset,
                    updateDataset = true
                )
            },
            {
                onClickMapButton(dataset)
            }
        )
        if (
            DataSetFilter.entries.map { entry ->
                entry.name
            }.contains(dataset.dataSet)
        ) {
            isEnabled.add(viewState.features.isNotEmpty())
            menuLabels.add(stringResource(R.string.menu_filter_by))
            onClickActions.add({ showFilterByDialog = true })
        }
        /* End DynamicActionMenu */

        Scaffold(topBar = {
            TwoLineAppBar(
                actions = {
                    DynamicActionMenu(
                        isEnabled = isEnabled,
                        menuLabels = menuLabels,
                        onClickActions = onClickActions
                    )
                },
                onClickBackButton = onClickBackButton,
                title = stringResource(
                    when (viewState.filter) {
                        null -> R.string.app_alias
                        else -> R.string.app_alias_filtered
                    }
                ),
                subtitle = getFeaturesSubtitle(
                    dataset = dataset,
                    ftsSize = viewState.features.size,
                    ftsDloaded = viewState.ftsDloaded,
                    xCount = dataset.xCount
                )
            )
        }) { contentPadding ->

            when (viewState.uiState) {
                DataSetUiState.Downloading -> LoadingScreen(
                    modifier = modifier,
                    stringId = R.string.loading_from_doc
                )
                DataSetUiState.Error -> ErrorScreen(
                    errorMessage = viewState.message,
                    modifier = modifier,
                    retryAction = {
                        datasetViewModel.setCurrentDataSet(dataset)
                    }
                )
                DataSetUiState.Invalid -> InvalidScreen(
                    modifier = modifier,
                )
                DataSetUiState.Loading -> LoadingScreen(
                    modifier = modifier,
                    stringId = R.string.loading_from_file
                )
                DataSetUiState.NotFound -> NotFoundScreen(
                    dataset = dataset,
                    modifier = modifier,
                )
                DataSetUiState.GettingMore,
                DataSetUiState.None,
                DataSetUiState.Success -> FeatureList(
                    contentPadding = contentPadding,
                    datasetLazyListState = 
                        datasetViewModel.datasetLazyListState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            bottom = paddingVertical,
                            end = paddingHorizontal,
                            start = paddingHorizontal
                        ),
                    onClickDownloadMore = {
                        datasetViewModel.getFeatureCollection(
                            dataset = dataset,
                            startFrom = viewState.features.size
                        )
                    },
                    onClickFeature = { feature ->
                        onClickFeature(
                            dataset,
                            viewState,
                            viewState.features.indexOf(feature)
                        )
                    },
                    onLongClickFeature = { feature ->
                        onLongClickFeature(
                            dataset,
                            feature
                        )
                    },
                    paddingHorizontal = paddingHorizontal,
                    paddingVertical = paddingVertical,
                    viewState = viewState
                )
                DataSetUiState.Unavailable -> ErrorScreen(
                    errorMessage = viewState.message,
                    modifier = modifier /* No retry button */
                )
            }
        }
    }
}