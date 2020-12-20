package com.github.williamfzc.webvpm

import android.util.Log
import android.webkit.ValueCallback
import android.webkit.WebView


object WvpmCore {
    private val TAG = "WvpmCore"

    fun applyWhenFinished(view: WebView?, url: String?, callback: (String) -> Unit) {
        Log.d(TAG, "page finished: $url")
        val manager = WvpmJsManager.getInst(view?.context)
        manager ?: run {
            Log.e(TAG, "something wrong when loading js assets")
            return
        }

        view?.run {
            this.evaluateJavascript(manager.jsPerf.content, ValueCallback {
                Log.d(TAG, "js ret: $it")
                callback(it)
            })
        }
    }
}