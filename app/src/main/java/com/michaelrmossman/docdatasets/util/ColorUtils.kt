package com.michaelrmossman.docdatasets.util

import android.graphics.Color.argb
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.michaelrmossman.docdatasets.R
import kotlin.random.Random

/**
 * Custom color utilities used throughout the app
 */
object ColorUtils {

    @Composable
    fun getEmptyListColor(): Color {
        return colorResource(R.color.empty_list)
    }
}