package com.michaelrmossman.docdatasets.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.michaelrmossman.docdatasets.R
import com.michaelrmossman.docdatasets.data.DataSetEntity
import com.michaelrmossman.docdatasets.enum.DataSet
import com.michaelrmossman.docdatasets.ui.theme.DoCDatasetsTheme
import com.michaelrmossman.docdatasets.util.ColorUtils.getEmptyListColor
import com.michaelrmossman.docdatasets.util.ModifierUtils.downloading
import com.michaelrmossman.docdatasets.util.TextUtils.fontDimensionResource
import com.michaelrmossman.docdatasets.util.fromHtml

@Composable
fun ErrorScreen(
    modifier: Modifier = Modifier,
    errorMessage: String? = null,
    retryAction: (() -> Unit)? = null
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.outline_cloud_off_24),
            contentDescription = null,
            modifier = Modifier.size(
                dimensionResource(R.dimen.icon_size_giant)
            ),
            colorFilter = ColorFilter.tint(
                color = getEmptyListColor(),
                blendMode = BlendMode.SrcIn
            )
        )
        Text(
            text = stringResource(R.string.loading_failed),
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(
                dimensionResource(R.dimen.padding_large)
            )
        )
        retryAction?.let { retry ->
            Button(onClick = retry) {
                Text(stringResource(R.string.loading_retry))
            }
        }
        errorMessage?.let { message ->
            Text(
                fontSize = fontDimensionResource(
                    R.dimen.font_size_error
                ),
                text = message,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(
                    dimensionResource(R.dimen.padding_large)
                )
            )
        }
    }
}

@Composable
fun InvalidScreen(
    modifier: Modifier = Modifier
) {
    val message = stringResource(R.string.dataset_invalid)

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            contentDescription = null,
            painter = painterResource(R.drawable.outline_error_24),
            modifier = Modifier.size(
                dimensionResource(R.dimen.icon_size_giant)
            ),
            colorFilter = ColorFilter.tint(
                color = getEmptyListColor(),
                blendMode = BlendMode.SrcIn
            )
        )
        Text(
            text = message.fromHtml(),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(
                dimensionResource(R.dimen.padding_large)
            )
        )
    }
}

@Composable
fun LoadingScreen(
    @StringRes stringId: Int,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            contentDescription = stringResource(stringId),
            painter = painterResource(R.drawable.loading_image),
            modifier = Modifier
                .size(
                    dimensionResource(R.dimen.loading_anim_large)
                )
                /* Refer custom modifier in ModifierUtils */
                .downloading(isDownloading = true)
        )
        Text(
            text = stringResource(stringId),
            modifier = Modifier.padding(
                dimensionResource(R.dimen.padding_large)
            )
        )
    }
}

@Composable
fun NotFoundScreen(
    dataset: DataSetEntity,
    modifier: Modifier = Modifier
) {
    val message = stringResource(
        R.string.dataset_not_found_message,
        dataset.title
    )

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.outline_warning_24),
            contentDescription = null,
            modifier = Modifier.size(
                dimensionResource(R.dimen.icon_size_giant)
            ),
            colorFilter = ColorFilter.tint(
                color = getEmptyListColor(),
                blendMode = BlendMode.SrcIn
            )
        )
        Text(
            text = message.fromHtml(),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(
                dimensionResource(R.dimen.padding_mega)
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ErrorScreenPreview() {
    DoCDatasetsTheme {
        ErrorScreen(
            /* errorMessage | retryAction are nullable */
        )
    }
}

@Preview(showBackground = true)
@Composable
fun InvalidScreenPreview() {
    DoCDatasetsTheme {
        InvalidScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun LoadingScreenPreview() {
    DoCDatasetsTheme {
        LoadingScreen(
            stringId = R.string.loading_from_doc
        )
    }
}

@Preview(showBackground = true)
@Composable
fun NotFoundScreenPreview() {
    DoCDatasetsTheme {
        NotFoundScreen(
            dataset = DataSetEntity(
                id = 0,
                title = "AATH Concessions",
                dataSet = DataSet.AATH.name,
                dataType = 5,
                description = String(),
                extras = null,
                multiMap = false,
                outFields = String(),
                resId = String(),
                resLayer = 0,
                webUrl1 = String(),
                webUrl2 = String(),
                xCount = 123
            )
        )
    }
}