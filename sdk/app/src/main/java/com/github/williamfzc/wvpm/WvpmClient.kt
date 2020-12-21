package com.github.williamfzc.wvpm

import android.webkit.WebView
import android.webkit.WebViewClient
import com.github.williamfzc.wvpm.js.WvpmJsFlag

open class WvpmClient(
    private val originClient: WebViewClient?,
    private val targetJs: WvpmJsFlag,
    private val callback: WvpmCallback?
) : WebViewClient() {
    override fun shouldOverrideUrlLoading(view: WebView?, url: String): Boolean {
        originClient?.run {
            return this.shouldOverrideUrlLoading(view, url)
        } ?: run {
            return super.shouldOverrideUrlLoading(view, url)
        }
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        originClient?.run {
            this.onPageFinished(view, url)
        } ?: run {
            super.onPageFinished(view, url)
        }
        // inject main contents
        WvpmCore.applyWhenFinished(view, url, targetJs, callback)
    }
}
