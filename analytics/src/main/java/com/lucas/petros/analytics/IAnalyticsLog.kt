package com.lucas.petros.analytics

import android.os.Bundle

interface IAnalyticsLog {
    fun logEvent(name: String, params: Bundle? = null)
}
