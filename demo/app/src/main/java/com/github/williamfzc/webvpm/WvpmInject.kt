package com.github.williamfzc.webvpm

import android.util.Log
import android.webkit.WebView


object WvpmInject {
    private val TAG = "WvpmInject"

    fun inject(wv: WebView, callback: ((String) -> Unit)?) {
        Log.d(TAG, "injecting settings ...")
        // settings
        wv.settings?.let {
            // collect data via js
            @SuppressWarnings
            it.javaScriptEnabled = true
            it.domStorageEnabled = true
        }


        Log.d(TAG, "injecting webview client ...")
        // client
        val originPageFinished = wv.webViewClient::onPageFinished
        wv.webViewClient = object : WvpmClient() {
            override fun callback(jsReturn: String) {
                callback?.run {
                    callback(jsReturn)
                } ?: run {
                    super.callback(jsReturn)
                }
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                // some webviews may have their own clients
                originPageFinished.invoke(view, url)
            }
        }
        Log.d(TAG, "inject finished")
    }
}
