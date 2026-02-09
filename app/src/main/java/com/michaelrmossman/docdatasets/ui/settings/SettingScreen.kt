package com.michaelrmossman.docdatasets.ui.settings

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.integerArrayResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.michaelrmossman.docdatasets.R
import com.michaelrmossman.docdatasets.ui.components.DynamicActionMenu
import com.michaelrmossman.docdatasets.ui.components.LargeDropdownMenu
import com.michaelrmossman.docdatasets.ui.components.TwoLineAppBar
import com.michaelrmossman.docdatasets.util.DialogUtils.ConfirmResetSettingsDialog
import com.michaelrmossman.docdatasets.util.fromHtml

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(
    onClickBackButton: () -> Unit
) {
    val settingViewModel: SettingViewModel = viewModel(
        factory = SettingViewModel.Factory()
    )
    val disablePopups by settingViewModel.disablePopups.observeAsState()
    /* Not actual size, but index of integer-array */
    val resultsSize by settingViewModel.resultsSize.observeAsState()
    val filterCount by settingViewModel.filterCount.observeAsState()
    val filterCountExists by remember {
        derivedStateOf { (filterCount != 0) }
    }
    val satelliteView by settingViewModel.satelliteView.observeAsState()
    val scrollState = rememberScrollState()
    val settingsNotDefault by
        settingViewModel.settingsNotDefault.observeAsState(initial = false)
    var showResetDialog by remember { mutableStateOf(false) }
    val viewState by settingViewModel.settingUiState.collectAsState()

//    remember {
//        derivedStateOf {
//            disablePopups?.let { disable -> disable != 0 } == true
//            ||
//            resultsSize?.let { index -> index != 0 } == true
//            ||
//            satelliteView?.let { satellite -> satellite != 0 } == true
//        }
//    }

    val context = LocalContext.current
    LaunchedEffect(key1 = viewState.filterItemsDeleted) {
        /* Watch for a flag indicating that filter items
           were cleared... when present, show message */
        if (viewState.filterItemsDeleted != 0) {
            settingViewModel.resetFilterState()
            Toast.makeText(
                context,
                R.string.settings_toast_done,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    Scaffold(
        topBar = {
            TwoLineAppBar(
                actions = {
                    DynamicActionMenu(
                        isEnabled = listOf(
                            filterCountExists,
                            settingsNotDefault
                        ),
                        menuLabels = listOf(
                            R.string.menu_clear_filters,
                            R.string.menu_reset_settings
                        ).map { stringId ->
                            stringResource(stringId)
                        },
                        onClickActions = listOf(
                            { settingViewModel.clearFilterItems() },
                            { showResetDialog = true }
                        )
                    )
                },
                onClickBackButton = onClickBackButton,
                title = stringResource(R.string.app_name),
                subtitle = stringResource(R.string.menu_app_settings)
            )
        }
    ) { contentPadding ->

        if (showResetDialog) {
            ConfirmResetSettingsDialog(
                onClickConfirm = {
                    showResetDialog = false
                    settingViewModel.resetAllSettings()
                    onClickBackButton()
                },
                onClickDismiss = { showResetDialog = false }
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(
                dimensionResource(R.dimen.spacing_vertical)
            ),
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    bottom = contentPadding.calculateBottomPadding(),
                    top = contentPadding.calculateTopPadding()
                )
                .verticalScroll(scrollState)
        ) {

            resultsSize?.let { size ->
                /* No divider for top item */
                SettingMenu(
                    modifier = Modifier.padding(
                        end = dimensionResource(R.dimen.padding_micro),
                        start = dimensionResource(R.dimen.padding_micro),
                        top = dimensionResource(R.dimen.padding_small)
                    ),
                    settingLabelId = R.string.setting_num_results_label,
                    settingStringId = R.string.setting_num_results_text,
                    settingIndex = size,
                    settings = integerArrayResource(
                        R.array.settings_num_results
                    ).map { setting -> setting.toString() },
                    saveSetting = { menuIndex ->
                        settingViewModel.setResultsSize(
                            index = menuIndex
                        )
                    }
                )
            }

            disablePopups?.let { disable ->
                SettingDivider()
                SettingSwitch(
                    onSwitch = { isChecked ->
                        settingViewModel.setDisablePopups(
                            disable = isChecked
                        )
                    },
                    settingStringId = R.string.setting_disable_popups,
                    switchedOn = disable
                )
            }

            satelliteView?.let { satellite ->
                SettingDivider()
                SettingSwitch(
                    onSwitch = { isChecked ->
                        settingViewModel.setSatelliteView(
                            satellite = isChecked
                        )
                    },
                    settingStringId = R.string.setting_show_satellite_view,
                    switchedOn = satellite
                )
            }
        }
    }
}

@Composable
fun SettingDivider(
    modifier: Modifier = Modifier
) {
    val dividerPadding = dimensionResource(R.dimen.padding_small)

    HorizontalDivider(
        modifier = modifier.padding(vertical = dividerPadding)
    )
}

@Composable
fun SettingMenu(
    @StringRes settingLabelId: Int,
    @StringRes settingStringId: Int,
    settingIndex: Int,
    settings: List<String>,
    saveSetting: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val horizontalPadding = dimensionResource(R.dimen.padding_small)
    var menuIndex by rememberSaveable {
        mutableIntStateOf(settingIndex)
    }

    Text(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = horizontalPadding),
        text = stringResource(settingStringId)
    )
    LargeDropdownMenu(
        label = stringResource(settingLabelId),
        items = settings,
        selectedIndex = menuIndex,
        onItemSelected = { index, _ ->
            menuIndex = index
            saveSetting(index)
        },
        modifier = Modifier.padding(horizontal = horizontalPadding)
    )
}

@Composable
fun SettingSwitch(
    onSwitch: (Int) -> Unit,
    @StringRes settingStringId: Int,
    switchedOn: Int,
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true
) {
    val rowHorizontalPadding = dimensionResource(R.dimen.padding_small)
    val textHorizontalPadding = dimensionResource(R.dimen.padding_mini)
    val verticalPadding = dimensionResource(R.dimen.padding_none)
    var switchItemSwitchedOn by rememberSaveable {
        /* Allow for random debug vals */
        mutableStateOf(switchedOn > 0)
    }

    Row(
        modifier = modifier.padding(
            end = rowHorizontalPadding,
            start = rowHorizontalPadding,
            top = verticalPadding
        ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .padding(horizontal = textHorizontalPadding)
                .weight(1F),
            text = stringResource(settingStringId).fromHtml()
        )
        Switch(
            checked = switchItemSwitchedOn,
            enabled = isEnabled,
            onCheckedChange = { isChecked ->
                switchItemSwitchedOn = isChecked
                onSwitch(
                    when (isChecked) {
                        true -> 1
                        else -> 0
                    }
                )
            },
            thumbContent = when (switchItemSwitchedOn) {
                false -> null
                else -> {
                    {
                        Icon(
                            imageVector = Icons.Filled.Check,
                            contentDescription = null,
                            modifier = Modifier.size(
                                SwitchDefaults.IconSize
                            )
                        )
                    }
                }
            }
        )
    }
}