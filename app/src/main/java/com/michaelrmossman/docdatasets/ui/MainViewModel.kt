package com.michaelrmossman.docdatasets.ui

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.michaelrmossman.docdatasets.navigation.CurrentScreen

/* Used for Navigation3 app navigation */
class MainViewModel: ViewModel() {

    val homeScreen = CurrentScreen.DataTypeList

    val backStack = mutableStateListOf<CurrentScreen>(
        homeScreen
    )

    /* To avoid the double-tap BackButton exception, (on
       screens that are called directly from home screen)
       remove all backstack entries except homeScreen */
    fun home() {
        backStack.forEach { screen ->
            backStack.removeIf { screen ->
                screen != homeScreen
            }
        }
    }

    fun pop() {
        backStack.removeLastOrNull()
    }

    fun put(currentScreen: CurrentScreen) {
        backStack.add(currentScreen)
    }
}