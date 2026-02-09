package com.michaelrmossman.docdatasets.ui.datasets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.michaelrmossman.docdatasets.R
import com.michaelrmossman.docdatasets.data.DataSetEntity
import com.michaelrmossman.docdatasets.state.DataSetListState
import com.michaelrmossman.docdatasets.ui.components.BottomSheetDescription
import com.michaelrmossman.docdatasets.ui.components.TwoLineAppBar
import com.michaelrmossman.docdatasets.util.formatWithCommas

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DataSetListScreen(
    datasetViewModel: DataSetViewModel,
    onClickBackButton: () -> Unit,
    onClickDataSet: (DataSetEntity, DataSetListState, Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val lazyListState = rememberLazyListState()
    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
    lifecycleOwner.lifecycle.addObserver(datasetViewModel)
    val paddingHorizontal = dimensionResource(
        R.dimen.padding_mini
    )
    val paddingVertical = dimensionResource(
        R.dimen.padding_small
    )
    var showBSDataSet by remember { mutableStateOf<DataSetEntity?>(null) }
    val snackbarActionLabel = stringResource(
        R.string.snack_bar_action_got_it
    )
    val snackbarHostState = remember { SnackbarHostState() }
    val snackbarMessage = stringResource(
        R.string.snack_bar_long_press_for_descr
    )
    val spacingVertical = dimensionResource(
        R.dimen.padding_small
    )
    val viewState by
        datasetViewModel.datasetListState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = Unit) {
        if (datasetViewModel.showLongPressForFullDescr()) {
            snackbarHostState.showSnackbar(
                message = snackbarMessage,
                actionLabel = snackbarActionLabel,
                duration = SnackbarDuration.Indefinite
            )
        }
    }

    showBSDataSet?.let { dataset ->
        BottomSheetDescription(
            dataset = dataset,
            onDismissRequest = { showBSDataSet = null }
        )
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            TwoLineAppBar(
                actions = { /* TODO */ },
                onClickBackButton = onClickBackButton,
                title = pluralStringResource(
                    R.plurals.app_alias,
                    viewState.datasets.size
                ),
                subtitle = stringResource(
                    R.string.dataset_subtitle,
                    viewState.dataType.title,
                    viewState.datasets.size.formatWithCommas()
                )
            )
        }
    ) { contentPadding ->

        LazyColumn(
            contentPadding = contentPadding,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .fillMaxSize()
                .padding(
                    horizontal = paddingHorizontal,
                    vertical = paddingVertical
                ),
            state = lazyListState,
            verticalArrangement = Arrangement.spacedBy(
                spacingVertical
            )
        ) {

            itemsIndexed(
                items = viewState.datasets
            ) { _, dataset ->

                ListItemDataSet(
                    dataset = dataset,
                    modifier = Modifier.fillMaxWidth(),
                    onClickDataSet = {
                        onClickDataSet(
                            dataset,
                            viewState,
                            viewState.datasets.indexOf(dataset)
                        )
                    },
                    onLongClickDataSet = {
                        snackbarHostState.currentSnackbarData?.dismiss()
                        showBSDataSet = dataset
                    }
                )
            }
        }
    }
}