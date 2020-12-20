package com.github.williamfzc.wvpm

import android.webkit.WebView
import android.webkit.WebViewClient
import com.github.williamfzc.wvpm.js.WvpmJsFlag

open class WvpmClient : WebViewClient() {
    // by default
    open var targetJs = WvpmJsFlag.FLAG_JS_PERF

    open fun callback(jsReturn: String) {
        // do nothing by default
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
        // inject main contents
        WvpmCore.applyWhenFinished(view, url, targetJs, this::callback)
    }
}
