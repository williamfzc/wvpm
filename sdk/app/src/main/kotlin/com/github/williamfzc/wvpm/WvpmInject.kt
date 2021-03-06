package com.github.williamfzc.wvpm

import android.os.Build
import android.util.Log
import android.webkit.WebView
import androidx.annotation.RequiresApi
import com.github.williamfzc.wvpm.js.WvpmJsInterface


internal object WvpmInject {
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
        task: WvpmTask
    ) {
        // inject js interface, must before loadUrl
        // see: https://developer.android.com/reference/android/webkit/WebView#addJavascriptInterface(java.lang.Object,%20java.lang.String)
        if (task.jsFlag is WvpmJsInterfaceFlag)
            wv.addJavascriptInterface(WvpmJsInterface, WvpmJsInterface.JS_OBJECT_NAME)

        // inject custom hooks
        wv.webViewClient = WvpmClient(
            wv.webViewClient,
            mapOf(task.location to listOf(task))
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun inject(
        wv: WebView,
        task: WvpmTask
    ) {
        Log.d(TAG, "inject task: ${task.jsFlag} to ${task.location}")
        Log.d(TAG, "injecting settings ...")
        injectSettings(wv)

        Log.d(TAG, "injecting webview client ...")
        injectHooks(wv, task)

        Log.d(TAG, "inject finished")
    }
}
