package com.michaelrmossman.docdatasets.util

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.michaelrmossman.docdatasets.R
import com.michaelrmossman.docdatasets.ui.theme.DoCDatasetsTheme
import com.michaelrmossman.docdatasets.util.DialogUtils.ConfirmResetSettingsDialog
import com.michaelrmossman.docdatasets.util.DialogUtils.FilterByDialog

/**
 * Dialog utility functions used throughout the app
 */
object DialogUtils {

    // const val TAG = "DialogUtils"

    @Composable
    fun CommonSimpleDialog(
        onClickConfirm: () -> Unit,
        onClickDismiss: () -> Unit,
        @StringRes confirmId: Int,
        @StringRes dismissId: Int,
        @StringRes textId: Int,
        @StringRes titleId: Int,
        modifier: Modifier = Modifier
    ) {
        AlertDialog(
            modifier = modifier,
            onDismissRequest = {
                // Dismiss the dialog when the user clicks outside the
                // dialog or on the back button. If you want to disable
                // that functionality, simply use empty onDismissRequest
                onClickDismiss()
            },
            title = {
                Text(text = stringResource(titleId).plus("?"))
            },
            text = {
                Text(
                    text = stringResource(textId).fromHtml(),
                    textAlign = TextAlign.Justify
                )
            },
            dismissButton = {
                TextButton (
                    onClick = { onClickDismiss() }
                ) {
                    Text(text = stringResource(dismissId))
                }
            },
            confirmButton = {
                TextButton(
                    onClick = { onClickConfirm() }
                ) {
                    Text(text = stringResource(confirmId))
                }
            }
        )
    }

    @Composable
    fun ConfirmResetSettingsDialog(
        onClickConfirm: () -> Unit,
        onClickDismiss: () -> Unit,
        modifier: Modifier = Modifier
    ) {
        CommonSimpleDialog(
            confirmId = R.string.dialog_confirm,
            dismissId = R.string.dialog_cancel,
            modifier = modifier,
            onClickConfirm = onClickConfirm,
            onClickDismiss = onClickDismiss,
            textId = R.string.settings_reset_message,
            titleId = R.string.menu_reset_settings
        )
    }

    @Composable
    private fun DialogListItem(
        paddingHorizontal: Dp,
        index: Int,
        isSelected: Boolean,
        onClickConfirm: (String?) -> Unit,
        item: String,
        paddingVertical: Dp,
        modifier: Modifier = Modifier
    ) {
        Row(
            modifier = modifier.clickable {
                onClickConfirm(
                    when (index) {
                        0 -> null
                        else -> item
                    }
                )
            },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                contentDescription = null,
                modifier = Modifier.padding(
                    end = paddingHorizontal,
                    top = when (index) {
                        0 -> 0.dp
                        else -> paddingVertical
                    }
                ),
                painter = painterResource(
                    when (isSelected) {
                        true -> R.drawable.outline_radio_button_checked_24
                        else -> R.drawable.outline_radio_button_unchecked_24
                    }
                )
            )
            Text(
                text = item,
                modifier = Modifier.padding(
                    top = when (index) {
                        0 -> 0.dp
                        else -> paddingVertical
                    }
                )
            )
        }
    }

    @Composable
    fun FilterByDialog(
        currentSelection: Int,
        filterByListItems: List<String?>,
        headerText: String,
        onClickConfirm: (String?) -> Unit,
        onClickDismiss: () -> Unit,
        @StringRes stringId: Int,
        modifier: Modifier = Modifier
    ) {
        val listState = rememberLazyListState()
        val paddingHorizontal = dimensionResource(
            R.dimen.padding_horizontal
        )
        val paddingVertical = dimensionResource(
            R.dimen.padding_vertical
        )
        val spacingVertical = dimensionResource(
            R.dimen.spacing_vertical
        )
        AlertDialog(
            modifier = modifier,
            onDismissRequest = {
                /* Refer note at BOF */
                onClickDismiss()
            },
            title = {
                Text(
                    text = stringResource(
                        stringId,
                        headerText
                    ).fromHtml()
                )
            },
            text = {
                LazyColumn(
                    modifier = Modifier
                        .padding(horizontal = paddingHorizontal)
                        /* Dicey, but without it, lazy
                           composition makes list jump */
                        .width(280.dp),
                    state = listState,
                    verticalArrangement = Arrangement.spacedBy(
                        spacingVertical
                    )
                ) {
                    itemsIndexed(
                        items = filterByListItems
                    ) { index, listItem ->
                        listItem?.let { item ->
                            DialogListItem(
                                paddingHorizontal = paddingHorizontal,
                                index = index,
                                isSelected = (currentSelection == index),
                                onClickConfirm = onClickConfirm,
                                item = item,
                                paddingVertical = paddingVertical
                            )
                        }
                    }
                }
            },
            confirmButton = {
                TextButton (onClick = { onClickDismiss() }) {
                    Text(text = stringResource(R.string.dialog_cancel))
                }
            }
        )
        /* Jump down to selected item, if appropriate */
        if (currentSelection > 0) {
            LaunchedEffect(key1 = Unit) {
                listState.scrollToItem(currentSelection)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ConfirmResetPreview() {
    DoCDatasetsTheme {
        ConfirmResetSettingsDialog(
            onClickConfirm = {},
            onClickDismiss = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun FilterDialogPreview() {
    DoCDatasetsTheme {
        FilterByDialog(
            currentSelection = 2,
            filterByListItems = listOf(
                "Auckland",
                "Wellington",
                "Christchurch",
                "Dunedin"
            ),
            headerText = stringResource(
                R.string.prop_region
            ),
            onClickConfirm = {},
            onClickDismiss = {},
            stringId =
                R.string.filter_header_text
        )
    }
}