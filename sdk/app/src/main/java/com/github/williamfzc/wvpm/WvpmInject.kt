package com.github.williamfzc.wvpm

import android.os.Build
import android.util.Log
import android.webkit.WebView
import androidx.annotation.RequiresApi
import com.github.williamfzc.wvpm.js.WvpmJsFlag


object WvpmInject {
    private val TAG = "WvpmInject"

    @RequiresApi(Build.VERSION_CODES.O)
    fun inject(wv: WebView, targetJs: WvpmJsFlag, callback: ((String) -> Unit)?) {
        Log.d(TAG, "injecting settings ...")
        // settings
        wv.settings.let {
            // collect data via js
            @SuppressWarnings
            it.javaScriptEnabled = true
            it.domStorageEnabled = true
        }

        Log.d(TAG, "injecting webview client ...")
        // client
        wv.webViewClient = WvpmClient(wv.webViewClient, targetJs, callback)
        Log.d(TAG, "inject finished")
    }
}
