package com.michaelrmossman.docdatasets.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.michaelrmossman.docdatasets.data.DataSetEntity
import com.michaelrmossman.docdatasets.R
import com.michaelrmossman.docdatasets.ui.datasets.ListItemDataSetContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetDescription(
    dataset: DataSetEntity,
    onDismissRequest: () -> Unit,
    /* Modifier used by all [Feature] text items */
    modifier: Modifier = Modifier
) {
    val bottomSheetState = rememberModalBottomSheetState()
    val headerText = stringResource(R.string.menu_dataset_desc)
    val iconPadding = dimensionResource(R.dimen.padding_small)
    val iconSize = dimensionResource(R.dimen.icon_size_small)
    val paddingHorizontal = dimensionResource(R.dimen.padding_horizontal)
    val paddingVertical = dimensionResource(R.dimen.padding_vertical)
    val scrollState = rememberScrollState()
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
                )
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(spacingVertical)
        ) {

            Row(
                modifier = modifier.padding(top = paddingVertical),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    headerText,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(
                            horizontal = dimensionResource(
                                R.dimen.padding_list_item
                            )
                        )
                        .weight(1F)
                )
                IconButton(
                    modifier = Modifier
                        .padding(horizontal = iconPadding)
                        .size(iconSize),
                    onClick = { onDismissRequest() }
                ) {
                    Icon(
                        painter = painterResource(
                            R.drawable.outline_close_24
                        ),
                        contentDescription = stringResource(
                            R.string.bottom_sheet_dismiss_desc
                        )
                    )
                }
            }

            ListItemDataSetContent(
                dataset = dataset,
                fullText = true,
                modifier = modifier
            )
        }
    }
}