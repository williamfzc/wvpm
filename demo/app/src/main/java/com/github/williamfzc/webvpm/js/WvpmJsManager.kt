package com.github.williamfzc.webvpm.js

import android.content.Context
import android.util.Log
import android.webkit.ValueCallback
import android.webkit.WebView


enum class WvpmJsFlag {
    FLAG_JS_PERF
}


object WvpmJsManager {
    private val TAG = "WvpmJsManager"
    var jsMap = mutableMapOf<WvpmJsFlag, WvpmJsContent?>(
        WvpmJsFlag.FLAG_JS_PERF to null
    )
    var ready = false

    fun eval(wv: WebView, targetJsFlag: WvpmJsFlag, callback: ((String) -> Unit)?) {
        eval(wv, jsMap[targetJsFlag]?.content, callback)
    }

    fun eval(wv: WebView, targetJsRaw: String?, callback: ((String) -> Unit)?) {
        if (!ready)
            initInst(wv.context)
        callback?.let {
            wv.evaluateJavascript(targetJsRaw.orEmpty(), ValueCallback(it))
        }
    }

    fun eval(wv: WebView, callback: ((String) -> Unit)?) {
        for (each in jsMap.keys) {
            each.run {
                eval(
                    wv,
                    this,
                    callback
                )
            }
        }
    }

    private fun initInst(ctx: Context?): WvpmJsManager? {
        jsMap[WvpmJsFlag.FLAG_JS_PERF] =
            PerfWvpmJsContent(ctx = ctx)

        // check
        for (each in jsMap.values) {
            // already init but failed
            if (each?.ready != true) {
                Log.d(TAG, "something wrong when init $each")
                return null
            }
        }
        ready = true
        Log.d(TAG, "init inst ready")
        return this
    }
}
