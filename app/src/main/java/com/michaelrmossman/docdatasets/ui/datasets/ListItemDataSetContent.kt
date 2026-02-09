package com.michaelrmossman.docdatasets.ui.datasets

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import com.michaelrmossman.docdatasets.data.DataSetEntity
import com.michaelrmossman.docdatasets.R
import com.michaelrmossman.docdatasets.util.formatWithCommas
import com.michaelrmossman.docdatasets.util.fromHtml
import com.michaelrmossman.docdatasets.util.IconUtils.getDataTypeIconId
import com.michaelrmossman.docdatasets.util.IntegerUtils.getMaxLines

@Composable
fun ListItemDataSetContent(
    dataset: DataSetEntity,
    fullText: Boolean,
    modifier: Modifier = Modifier
) {
    val datasetTitleText = when (fullText) {
        /* fullText version does NOT include qty */
        true -> stringResource(
            R.string.dataset_list_item_desc,
            dataset.title
        )
        else -> stringResource(
            R.string.dataset_list_item,
            dataset.title,
            dataset.xCount.formatWithCommas()
        )
    }
    val descriptionMaxLines = getMaxLines(fullText)
    val descriptionText = stringResource(
        R.string.common_description,
        dataset.description
    )
    val drawableId = getDataTypeIconId(dataset.dataType)
    val iconPadding = dimensionResource(R.dimen.padding_mini)

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(drawableId),
            modifier = Modifier.padding(
                bottom = iconPadding,
                top = iconPadding,
                end = iconPadding
            ),
            contentDescription = stringResource(
                R.string.datatype_icon_desc
            )
        )
        Text(
            text = datasetTitleText.fromHtml(),
            modifier = Modifier
                .padding(
                    dimensionResource(R.dimen.padding_list_item)
                )
                .weight(1F)
        )
    }

    Text(
        text = descriptionText.fromHtml(),
        maxLines = descriptionMaxLines,
        overflow = TextOverflow.Ellipsis,
        modifier = modifier.padding(
            dimensionResource(R.dimen.padding_list_item)
        )
    )
}