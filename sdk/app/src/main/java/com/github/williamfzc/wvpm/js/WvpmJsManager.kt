package com.github.williamfzc.wvpm.js

import android.content.Context
import android.util.Log
import android.webkit.ValueCallback
import android.webkit.WebView
import com.github.williamfzc.wvpm.WvpmCallback
import com.github.williamfzc.wvpm.WvpmResponse


public enum class WvpmJsFlag {
    FLAG_JS_PERF
}


object WvpmJsManager {
    private val TAG = "WvpmJsManager"
    var jsMap = mutableMapOf<WvpmJsFlag, WvpmJsContent?>(
        WvpmJsFlag.FLAG_JS_PERF to null
    )
    var ready = false

    fun eval(wv: WebView, targetJsFlag: WvpmJsFlag, callback: WvpmCallback?) {
        Log.d(TAG, "trying to eval: $targetJsFlag")
        if (!ready)
            initInst(wv.context)
        eval(wv, jsMap[targetJsFlag]?.content, callback)
    }

    fun eval(wv: WebView, targetJsRaw: String?, callback: WvpmCallback?) {
        callback?.let {
            wv.evaluateJavascript(targetJsRaw.orEmpty(), ValueCallback {
                Log.d(TAG, "get response: $it")
                return@ValueCallback callback(WvpmResponse(it))
            })
        }
    }

    private fun initInst(ctx: Context?): WvpmJsManager? {
        // todo: weird design
        // init
        jsMap[WvpmJsFlag.FLAG_JS_PERF] =
            PerfWvpmJsContent(ctx = ctx)

        // check
        for (each in jsMap.values) {
            // already init but failed
            if (each?.ready != true) {
                Log.w(TAG, "something wrong when init $each")
                return null
            }
        }
        ready = true
        Log.d(TAG, "init inst ready")
        return this
    }
}
