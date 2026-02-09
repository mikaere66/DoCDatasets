package com.michaelrmossman.docdatasets.ui.datatypes

import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.michaelrmossman.docdatasets.R
import com.michaelrmossman.docdatasets.objects.DataTypeKt

@Composable
fun ListItemDataType(
    dataType: DataTypeKt,
    onClickDataType: () -> Unit,
    // onLongClickDataType: () -> Unit,
    modifier: Modifier = Modifier
) {
    val paddingHorizontal = dimensionResource(R.dimen.padding_horizontal)
    val paddingVertical = dimensionResource(R.dimen.padding_vertical)

    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = dimensionResource(R.dimen.card_elevation)
        ),
        onClick = { onClickDataType() },
        shape = RoundedCornerShape(
            dimensionResource(R.dimen.card_corner_shape)
        ),
//        modifier = Modifier.combinedClickable(
//            onClick = { onClickDataType() },
//            onLongClick = {
//                onLongClickDataType()
//            }
//        )
    ) {
        Column(
            modifier = Modifier.padding(
                horizontal = paddingHorizontal,
                vertical = paddingVertical
            ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(
                dimensionResource(R.dimen.padding_small)
            )
        ) {

            ListItemDataTypeContent(
                dataType = dataType,
                fullText = false,
                modifier = modifier
            )
        }
    }
}