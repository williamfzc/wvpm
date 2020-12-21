package com.github.williamfzc.wvpm

import android.util.Log
import android.webkit.WebView
import com.github.williamfzc.wvpm.js.WvpmJsFlag
import com.github.williamfzc.wvpm.js.WvpmJsManager


object WvpmCore {
    private val TAG = "WvpmCore"

    fun applyWhenFinished(view: WebView?, url: String?, targetJs: WvpmJsFlag, callback: ((String) -> Unit)?) {
        Log.d(TAG, "page finished: $url")

        view?.run {
            WvpmJsManager.eval(this, targetJs, callback)
        }
    }
}
