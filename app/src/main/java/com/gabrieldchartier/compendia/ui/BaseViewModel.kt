package com.gabrieldchartier.compendia.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

abstract class BaseViewModel<StateEvent, ViewState>: ViewModel() {

    protected val _stateEvent: MutableLiveData<StateEvent> = MutableLiveData()
    protected val _viewState: MutableLiveData<ViewState> = MutableLiveData()

    val viewState: LiveData<ViewState>
        get() = _viewState

    val dataState: LiveData<DataState<ViewState>> = Transformations
            .switchMap(_stateEvent) { stateEvent ->
                Log.d("BaseViewModel", " (line 19): triggering state event change switch map with state event = $stateEvent")
                stateEvent?.let {
                    Log.d("BaseViewModel", " (line 20): calling handleStateEvent with state event = $stateEvent")
                    handleStateEvent(stateEvent)
                }
            }

    fun setStateEvent(event: StateEvent) {
        Log.d("BaseViewModel", "setStateEvent (line 26): setting state event = $event")
        Log.d("BaseViewModel", "setStateEvent (line 28): data state is currently = ${dataState.value}")
        _stateEvent.value = event
        Log.d("BaseViewModel", "setStateEvent (line 30): data state after state event set is = ${dataState.value}")
    }

    fun getCurrentViewStateOrNew(): ViewState {
        val value = viewState.value?.let {
            it
        }?: initNewViewState()
        return value
    }

    abstract fun initNewViewState(): ViewState

    abstract fun handleStateEvent(stateEvent: StateEvent): LiveData<DataState<ViewState>>
}