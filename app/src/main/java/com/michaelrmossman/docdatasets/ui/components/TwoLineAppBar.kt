package com.michaelrmossman.docdatasets.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import com.michaelrmossman.docdatasets.R
import com.michaelrmossman.docdatasets.util.TextUtils.fontDimensionResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TwoLineAppBar(
    modifier: Modifier = Modifier,
    actions: @Composable RowScope.() -> Unit = {},
    onClickBackButton: (() -> Unit?)? = null,
    title: String,
    subtitle: String? = null
) {
    val subtitleFontSize = fontDimensionResource(R.dimen.subtitle_font_size)

    TopAppBar(
        actions = actions,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor =
                MaterialTheme.colorScheme.primaryContainer,
            titleContentColor =
                MaterialTheme.colorScheme.onPrimaryContainer
        ),
        modifier = modifier,
        /* navigationIcon not used if TopAppBar is @ centre of large screen */
        navigationIcon = {
            if (title.isNotBlank()) {
                onClickBackButton?.let { onClick ->
                    BackButton(onClickBackButton = { onClick() })
                }
            }
        },
        title = {
            Column {
                if (title.isNotBlank()) { /* Same as above : Title not used */
                    Text(
                        text = title,
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
                subtitle?.let { sub ->
                    Text(
                        fontSize = subtitleFontSize,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        text = sub
                    )
                }
            }
        }
    )
}