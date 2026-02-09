package com.michaelrmossman.docdatasets.state

sealed interface DataSetUiState {
    data object Downloading: DataSetUiState
    data object Error      : DataSetUiState
    data object GettingMore: DataSetUiState
    data object Invalid    : DataSetUiState
    data object Loading    : DataSetUiState
    data object None       : DataSetUiState
    data object NotFound   : DataSetUiState
    data object Success    : DataSetUiState
    data object Unavailable: DataSetUiState
}