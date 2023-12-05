package com.lucas.petros.commons.extension

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData

fun Fragment.observeAndNavigateBack(liveData: LiveData<Boolean>) {
    liveData.observe(viewLifecycleOwner) { mustNavigateBack ->
        if (mustNavigateBack.handleOpt()) {
            activity?.onBackPressedDispatcher?.onBackPressed()
        }
    }
}