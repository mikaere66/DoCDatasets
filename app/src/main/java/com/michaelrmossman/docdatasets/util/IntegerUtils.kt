package com.michaelrmossman.docdatasets.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.integerResource
import com.michaelrmossman.docdatasets.DoCDataSetsApplication.Companion.instance
import com.michaelrmossman.docdatasets.R

/**
 * Integer util functions used throughout the app
 */
object IntegerUtils {

    @Composable
    fun getMaxLines(fullText: Boolean): Int =
        when (fullText) {
            true -> Int.MAX_VALUE // Details
            else -> integerResource( // List
                id = R.integer.summary_max_lines
            )
        }

    fun getNumResults(numResultsSetting: Int): Int {
        val numResultSettings = instance.resources.getIntArray(
            R.array.settings_num_results
        )
        return numResultSettings[numResultsSetting]
    }
}