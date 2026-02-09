package com.michaelrmossman.docdatasets.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.michaelrmossman.docdatasets.R

@Composable
fun ItemImage(
    imageUrl: String,
    @StringRes contentDescrStringId: Int,
    modifier: Modifier = Modifier
) {
    val itemImageSize = dimensionResource(R.dimen.item_image_size_min)
    val paddingHorizontal = dimensionResource(R.dimen.padding_small)
    val paddingVertical = dimensionResource(R.dimen.padding_mini)
    val roundedCornerShape = dimensionResource(R.dimen.card_corner_shape)

    AsyncImage(
        contentDescription = stringResource(
            contentDescrStringId
        ),
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = itemImageSize)
            .padding(
                horizontal = paddingHorizontal,
                vertical = paddingVertical
            )
            .clip(RoundedCornerShape(roundedCornerShape)),
        model = ImageRequest.Builder(
            context = LocalContext.current
        )
        .data(imageUrl)
        .crossfade(true)
        .build()
    )
}