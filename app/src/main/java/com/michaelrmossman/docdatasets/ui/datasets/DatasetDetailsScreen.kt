package com.michaelrmossman.docdatasets.ui.datasets

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.michaelrmossman.docdatasets.R
import com.michaelrmossman.docdatasets.data.DataSetEntity
import com.michaelrmossman.docdatasets.model.Feature
import com.michaelrmossman.docdatasets.ui.components.TwoLineAppBar
import com.michaelrmossman.docdatasets.ui.features.FeatureItemPage

@Composable
fun DatasetDetailsScreen(
    dataset: DataSetEntity,
    // datasetListState: DataSetListState,
    datasetViewModel: DataSetViewModel,
    initialPage: Int,
    onClickBackButton: () -> Unit,
    windowWidthSize: WindowWidthSizeClass
) {
    // val features = datasetListState.features
    val paddingHorizontal = dimensionResource(R.dimen.padding_horizontal)
    val paddingVertical = dimensionResource(R.dimen.padding_vertical)
    val viewState by
        datasetViewModel.datasetListState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TwoLineAppBar(
                onClickBackButton = { onClickBackButton() },
                title = when (windowWidthSize) {
                    WindowWidthSizeClass.Compact -> {
                        stringResource(R.string.app_alias)
                    }
                    /* Pass zero as titleId for larger screens,
                       to indicate NO navigation or title text */
                    else -> String()
                },
//                subtitle = when (windowWidthSize) {
//                    WindowWidthSizeClass.Compact -> {
//                        getFeaturesSubtitle(
//                            dataset = dataset,
//                            ftsSize = datasetListState.features.size,
//                            ftsDloaded = datasetListState.ftsDloaded,
//                            xCount = dataset.xCount
//                        )
//                    }
//                    else -> null
//                }
            )
        }
    ) { contentPadding ->

        val content: (@Composable (Feature) -> Unit) = { feature ->
            FeatureItemPage(
                dataset = dataset,
                // dataType = viewState.dataType,
                feature = feature,
                modifier = Modifier.fillMaxSize(),
                paddingHorizontal = paddingHorizontal,
                paddingValues = contentPadding,
                paddingVertical = paddingVertical
            )
        }
        when (windowWidthSize == WindowWidthSizeClass.Compact) {
            true -> {
                var pageIndex by rememberSaveable {
                    mutableIntStateOf(initialPage)
                }
                val pagerState = rememberPagerState(
                    initialPage = pageIndex,
                    pageCount = { viewState.features.size }
                )
                LaunchedEffect(pagerState) {
                    // Collect from the snapshotFlow reading the currentPage
                    snapshotFlow { pagerState.currentPage }.collect { page ->
                        datasetViewModel.setCurrentDataSet(
                            dataset = viewState.datasets[page]
                        )
                        /* Store current index as pageIndex, in case of
                           activity recreation, e.g. screen rotation */
                        pageIndex = page
                    }
                }
                HorizontalPager(
                    state = pagerState,
                    pageSpacing = dimensionResource(
                        R.dimen.padding_page_spacing
                    ),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(
                            horizontal = paddingHorizontal,
                            vertical = paddingVertical
                        )
                ) { page ->
                    content(viewState.features[page])
                }
            }
            else -> content(viewState.features[initialPage])
        }
    }
}