package com.github.williamfzc.wvpm

import android.os.Build
import android.util.Log
import android.webkit.WebView
import androidx.annotation.RequiresApi
import com.github.williamfzc.wvpm.js.WvpmJsFlag


public enum class WvpmInjectLocation {
    FLAG_ON_PAGE_FINISHED,
    FLAG_ON_PAGE_STARTED,
}


object WvpmInject {
    private val TAG = "WvpmInject"

    fun injectSettings(wv: WebView) {
        wv.settings.let {
            // collect data via js
            @SuppressWarnings
            it.javaScriptEnabled = true
            it.domStorageEnabled = true
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun injectHooks(
        wv: WebView,
        targetJs: WvpmJsFlag,
        callback: WvpmCallback?,
        location: WvpmInjectLocation
    ) {
        // inject custom hooks
        wv.webViewClient = WvpmClient(
            wv.webViewClient,
            mapOf(location to listOf(WvpmTask(targetJs, callback)))
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun inject(
        wv: WebView,
        targetJs: WvpmJsFlag,
        callback: WvpmCallback?,
        location: WvpmInjectLocation
    ) {
        Log.d(TAG, "injecting settings ...")
        injectSettings(wv)

        Log.d(TAG, "injecting webview client ...")
        injectHooks(wv, targetJs, callback, location)

        Log.d(TAG, "inject finished")
    }
}
