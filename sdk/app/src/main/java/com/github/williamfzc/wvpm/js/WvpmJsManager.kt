package com.github.williamfzc.wvpm.js

import android.util.Log
import android.webkit.ValueCallback
import android.webkit.WebView
import com.github.williamfzc.wvpm.WvpmCallback
import com.github.williamfzc.wvpm.WvpmJsFlag
import com.github.williamfzc.wvpm.WvpmJsFlagBase
import com.github.williamfzc.wvpm.WvpmResponse


object WvpmJsManager {
    private val TAG = "WvpmJsManager"
    private var jsContentMap = mutableMapOf<WvpmJsFlagBase, WvpmJsContent>(
        WvpmJsFlag.FLAG_JS_PERF_TIMING to WvpmJsPerfTiming,
        WvpmJsFlag.FLAG_JS_PERF_NAVIGATION to WvpmJsPerfNavigation
    )

    fun addJs(flag: WvpmJsFlagBase, targetJsContent: WvpmJsContent) {
        jsContentMap[flag]?.run {
            Log.w(TAG, "flag $flag already existed")
        } ?: run {
            this.jsContentMap[flag] = targetJsContent
            Log.d(TAG, "add flag $flag finished")
        }
    }

    fun eval(wv: WebView, targetJsFlag: WvpmJsFlagBase, callback: WvpmCallback?) {
        Log.d(TAG, "trying to eval: $targetJsFlag")
        jsContentMap[targetJsFlag]?.run {
            if (!this.ready)
                this.initObject(wv.context)
            eval(wv, this.content, callback)
        }
    }

    fun eval(wv: WebView, targetJsRaw: String?, callback: WvpmCallback?) {
        callback?.let {
            wv.evaluateJavascript(targetJsRaw.orEmpty(), ValueCallback {
                Log.d(TAG, "get response: $it")
                return@ValueCallback callback(WvpmResponse(it))
            })
        }
    }
}
