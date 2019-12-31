package com.gabrieldchartier.compendia.ui

interface DataStateChangeListener {
    fun onDataStateChange(dataState: DataState<*>?)

    fun hideKeyboard()
}