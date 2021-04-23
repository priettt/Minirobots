package com.example.minirobots.utilities

import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class FlowObserver<T>(
    lifecycleOwner: LifecycleOwner,
    private val flow: Flow<T>,
    private val collector: suspend (T) -> Unit
) {

    private var job: Job? = null

    init {
        lifecycleOwner.lifecycle.addObserver(LifecycleEventObserver { source: LifecycleOwner, event: Lifecycle.Event ->
            when (event) {
                Lifecycle.Event.ON_START -> {
                    job = source.lifecycleScope.launch {
                        flow.collect { collector(it) }
                    }
                }
                Lifecycle.Event.ON_STOP -> {
                    job?.cancel()
                    job = null
                }
                else -> {
                }
            }
        })
    }
}

class FragmentObserver<T>(
    fragment: Fragment,
    private val flow: Flow<T>,
    private val collector: suspend (T) -> Unit
) {
    init {
        fragment.viewLifecycleOwnerLiveData.observe(
            fragment,
            Observer { viewLifeCycleOwner ->
                FlowObserver(viewLifeCycleOwner, flow, collector)
            }
        )
    }
}

inline fun <reified T> Flow<T>.observeIn(
    fragment: Fragment
) = FragmentObserver(fragment, this) {}