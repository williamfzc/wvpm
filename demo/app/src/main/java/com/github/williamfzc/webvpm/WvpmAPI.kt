package com.github.williamfzc.webvpm

import android.webkit.WebView

object WvpmAPI {
    fun wrapWebview(wv: WebView?) {
        wv?.run {
            WvpmInject.inject(this)
        }
    }
}