package com.github.williamfzc.webvpm

import android.util.Log
import android.webkit.WebView
import com.github.williamfzc.webvpm.js.WvpmJsFlag
import com.github.williamfzc.webvpm.js.WvpmJsManager


object WvpmCore {
    private val TAG = "WvpmCore"

    fun applyWhenFinished(view: WebView?, url: String?, targetJs: WvpmJsFlag, callback: (String) -> Unit) {
        Log.d(TAG, "page finished: $url")

        view?.run {
            WvpmJsManager.eval(this, targetJs, callback)
        }
    }
}
