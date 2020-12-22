package com.github.williamfzc.wvpm

import android.util.Log
import android.webkit.WebView
import com.github.williamfzc.wvpm.js.WvpmJsFlag
import com.github.williamfzc.wvpm.js.WvpmJsManager


object WvpmCore {
    private val TAG = "WvpmCore"

    fun apply(view: WebView?, url: String?, targetJs: WvpmJsFlag, callback: WvpmCallback?) {
        view?.run {
            WvpmJsManager.eval(this, targetJs, callback)
        }
    }
}
