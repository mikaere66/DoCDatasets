package com.michaelrmossman.docdatasets.util

import com.michaelrmossman.docdatasets.R

/**
 * Icon utility functions used throughout the app
 */
object IconUtils {

    fun getDataTypeIconId(dataTypeId: Int): Int {
        return when (dataTypeId) {
            /* Species Distrib */ 6 -> R.drawable.outline_scatter_plot_24
            /* Rec Hunting */     5 -> R.drawable.icons_lib_pig_24
            /* Outdoor Rec */     4 -> R.drawable.outline_hiking_24
            /* Marine Mammals */  3 -> R.drawable.icons_lib_fish_24
            /* Land Birds */      2 -> R.drawable.icons_lib_bird_24
            /* DoC Management */  1 -> R.drawable.outline_landscape_24
            /* (0) Home screen (or backstop, to prevent index out-of-bounds) */
            else -> R.drawable.icons_lib_organisation_24
        }
    }
}