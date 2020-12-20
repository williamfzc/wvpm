package com.github.williamfzc.webvpm

import android.webkit.WebView
import android.webkit.WebViewClient

open class WvpmClient : WebViewClient() {
    open fun callback(jsReturn: String) {
        // do nothing by default
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
        // inject main contents
        WvpmCore.applyWhenFinished(view, url, this::callback)
    }
}
