package com.michaelrmossman.docdatasets.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.layout.calculatePaneScaffoldDirective
import androidx.compose.material3.adaptive.navigation3.rememberListDetailSceneStrategy
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.michaelrmossman.docdatasets.navigation.CurrentScreen
import com.michaelrmossman.docdatasets.ui.datasets.DatasetDetailsScreen
import com.michaelrmossman.docdatasets.ui.datasets.DataSetListScreen
import com.michaelrmossman.docdatasets.ui.maps.DataSetMapScreen
import com.michaelrmossman.docdatasets.ui.datasets.DataSetViewModel
import com.michaelrmossman.docdatasets.ui.datatypes.DataTypeListScreen
import com.michaelrmossman.docdatasets.ui.features.FeatureDetailsScreen
import com.michaelrmossman.docdatasets.ui.features.FeatureListScreen
import com.michaelrmossman.docdatasets.ui.settings.SettingScreen

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun DatasetsApp(
    windowWidthSize: WindowWidthSizeClass,
    windowHeightSize: WindowHeightSizeClass
) {
    val mainViewModel: MainViewModel = viewModel()

    val adaptiveInfo = currentWindowAdaptiveInfo()
    val directive = remember(adaptiveInfo) {
        calculatePaneScaffoldDirective(adaptiveInfo)
            .copy(horizontalPartitionSpacerSize = 0.dp)
    }
    val listDetailStrategy = rememberListDetailSceneStrategy<NavKey>(
        directive = directive
    )

    NavDisplay(
        backStack = mainViewModel.backStack,
        entryDecorators = listOf(
            /* Add the default decorators, for
               managing scenes/saving state */
            rememberSaveableStateHolderNavEntryDecorator(),
            // Then add viewModel store decorator
            rememberViewModelStoreNavEntryDecorator()
        ),
        onBack = { mainViewModel.pop() },
        sceneStrategy = listDetailStrategy,

        entryProvider = entryProvider {
            /* Entries in alphabetical order, by CurrentScreen key */

            /* Feature screens called directly from DataSetList have
               viewModel scoped to that screen, to fix lazyListState */

            /* Refer note in MainViewModel re mainViewModel.pop() */

            entry<CurrentScreen.AppSettings> {
                SettingScreen(
                    onClickBackButton = { mainViewModel.home() }
                )
            }

            entry<CurrentScreen.DatasetDetails> { currentScreen ->
                DatasetDetailsScreen(
                    dataset = currentScreen.dataset,
                    datasetViewModel = viewModel(
                        factory = DataSetViewModel.Factory(
                            listState = currentScreen.datasetListState
                        )
                    ),
                    initialPage = currentScreen.initialPage,
                    onClickBackButton = { mainViewModel.pop() },
                    windowWidthSize = windowWidthSize
                )
            }

            entry<CurrentScreen.DataSetList> { currentScreen ->
                val datasetViewModel = viewModel<DataSetViewModel>(
                    factory = DataSetViewModel.Factory()
                )
                /* Load datasets ASAP, to avoid previous list showing briefly */
                datasetViewModel.setCurrentDataType(currentScreen.dataType)
                DataSetListScreen(
                    datasetViewModel = datasetViewModel,
                    onClickBackButton = { mainViewModel.home() },
                    onClickDataSet = { dataset, datasetListState, index ->
                        mainViewModel.put(
                            CurrentScreen.FeatureList(
                                dataset, datasetListState, index
                            )
                        )
                    }
                )
            }

            entry<CurrentScreen.DatasetMap> { currentScreen ->
                val datasetViewModel = viewModel<DataSetViewModel>(
                    factory = DataSetViewModel.Factory()
                )
                datasetViewModel.getGeometryByObjectId(
                    dataset = currentScreen.dataset,
                    feature = currentScreen.feature
                )
                DataSetMapScreen(
                    dataset = currentScreen.dataset,
                    datasetViewModel = datasetViewModel,
                    /* Modifier is for Loading, etc. screens */
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.background)
                        .fillMaxSize(),
                    onClickBackButton = { mainViewModel.pop() },
                    singleFeature = currentScreen.feature
                )
            }

            entry<CurrentScreen.DatasetMultiMap> { currentScreen ->
                val datasetViewModel = viewModel<DataSetViewModel>(
                    factory = DataSetViewModel.Factory()
                )
                datasetViewModel.getGeometryMultiMap(
                    dataset = currentScreen.dataset
                )
                DataSetMapScreen(
                    dataset = currentScreen.dataset,
                    datasetViewModel = datasetViewModel,
                    onClickBackButton = { mainViewModel.pop() },
                    /* Modifier is for Loading, etc. screens */
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.background)
                        .fillMaxSize(),
                )
            }

            entry<CurrentScreen.DataTypeList> {
                DataTypeListScreen(
                    datasetViewModel = viewModel<DataSetViewModel>(
                        factory = DataSetViewModel.Factory()
                    ),
                    onClickDataType = { dataType ->
                        mainViewModel.put(
                            CurrentScreen.DataSetList(
                                dataType = dataType
                            )
                        )
                    },
                    onClickSettings = {
                        mainViewModel.put(CurrentScreen.AppSettings)
                    }
                )
            }

            entry<CurrentScreen.FeatureDetails> { currentScreen ->
                FeatureDetailsScreen(
                    dataset = currentScreen.dataset,
                    datasetListState = currentScreen.datasetListState,
                    initialPage = currentScreen.initialPage,
                    onClickBackButton = { mainViewModel.pop() },
                    windowWidthSize = windowWidthSize
                )
            }

            entry<CurrentScreen.FeatureList> { currentScreen ->
                FeatureListScreen(
                    datasetViewModel = viewModel(
                        factory = DataSetViewModel.Factory(
                            dataset = currentScreen.dataset
                        )
                    ),
                    /* Modifier is for Loading, etc. screens */
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.background)
                        .fillMaxSize(),
                    onClickBackButton = { mainViewModel.pop() },
                    onClickFeature = { dataset, datasetListState, index ->
                        mainViewModel.put(
                            CurrentScreen.FeatureDetails(
                                dataset, datasetListState, index
                            )
                        )
                    },
                    onLongClickFeature = { dataset, feature ->
                        mainViewModel.put(
                            CurrentScreen.DatasetMap(
                                dataset = dataset,
                                feature = feature
                            )
                        )
                    },
                    onClickMapButton = { dataset ->
                        mainViewModel.put(
                            CurrentScreen.DatasetMultiMap(
                                dataset = dataset
                            )
                        )
                    }
                )
            }
        }
    )
}