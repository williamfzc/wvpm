package com.github.williamfzc.wvpm

import android.graphics.Bitmap
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
        wv.webViewClient = object : WvpmClient(wv.webViewClient) {

            // special hook after page finished
            override fun onPageFinished(view: WebView?, url: String?) {
                originClient?.run {
                    this.onPageFinished(view, url)
                } ?: run {
                    super.onPageFinished(view, url)
                }
                // injection
                if (location == WvpmInjectLocation.FLAG_ON_PAGE_FINISHED)
                    WvpmCore.apply(view, url, targetJs, callback)
            }

            // special hook before page rendered
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                originClient?.run {
                    this.onPageStarted(view, url, favicon)
                } ?: run {
                    super.onPageStarted(view, url, favicon)
                }
                // injection
                if (location == WvpmInjectLocation.FLAG_ON_PAGE_STARTED)
                    WvpmCore.apply(view, url, targetJs, callback)
            }
        }
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
