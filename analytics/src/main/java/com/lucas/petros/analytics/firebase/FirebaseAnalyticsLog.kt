package com.lucas.petros.analytics.firebase

import android.os.Bundle
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import com.lucas.petros.analytics.IAnalyticsLog

class FirebaseAnalyticsLog : IAnalyticsLog {
    override fun logEvent(name: String, params: Bundle?) {
        Firebase.analytics.logEvent(name, params)
    }
}
