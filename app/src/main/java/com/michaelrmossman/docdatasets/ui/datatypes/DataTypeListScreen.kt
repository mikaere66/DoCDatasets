package com.michaelrmossman.docdatasets.ui.datatypes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.michaelrmossman.docdatasets.R
import com.michaelrmossman.docdatasets.objects.DataTypeKt
import com.michaelrmossman.docdatasets.ui.components.SingleActionMenu
import com.michaelrmossman.docdatasets.ui.components.TwoLineAppBar
import com.michaelrmossman.docdatasets.ui.datasets.DataSetViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DataTypeListScreen(
    datasetViewModel: DataSetViewModel,
    onClickDataType: (DataTypeKt) -> Unit,
    onClickSettings: () -> Unit,
    modifier: Modifier = Modifier
) {
    val lazyListState = rememberLazyListState()
    val paddingHorizontal = dimensionResource(
        R.dimen.padding_mini
    )
    val paddingVertical = dimensionResource(
        R.dimen.padding_small
    )
    val spacingVertical = dimensionResource(
        R.dimen.padding_small
    )
    val viewState by
        datasetViewModel.datasetListState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TwoLineAppBar(
                actions = {
                    SingleActionMenu(
                        itemStringId = R.string.menu_app_settings,
                        onSingleItemClick = { onClickSettings() }
                    )
                },
                title = stringResource(R.string.app_name),
                subtitle = stringResource(
                    R.string.datatype_categories,
                    viewState.dataTypes.size
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
                items = viewState.dataTypes
            ) { _, dataType ->

                ListItemDataType(
                    dataType = dataType,
                    modifier = Modifier.fillMaxWidth(),
                    onClickDataType = {
                        onClickDataType(dataType)
                    },
//                    onLongClickDataType = {
//                        onLongClickDataType(dataType)
//                    }
                )
            }
        }
    }
}