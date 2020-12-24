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
            WvpmJsFlag.FLAG_JS_PERF_NAVIGATION to WvpmJsPerfNavigation,
            WvpmJsFlag.FLAG_JS_DEBUG_SAY_HI to WvpmJsDebugSayHi,
            WvpmJsFlag.FLAG_JS_DEBUG_FORMAT to WvpmJsDebugFormat
    )

    fun addJs(flag: WvpmJsFlagBase, targetJsContent: WvpmJsContent) {
        jsContentMap[flag]?.run {
            Log.w(TAG, "flag $flag already existed")
        } ?: run {
            this.jsContentMap[flag] = targetJsContent
            Log.d(TAG, "add flag $flag finished")
        }
    }

    fun removeJs(flag: WvpmJsFlagBase) = jsContentMap.remove(flag)

    fun eval(wv: WebView, targetJsFlag: WvpmJsFlagBase, callback: WvpmCallback?, jsArgs: Array<String>?) {
        Log.d(TAG, "trying to eval: $targetJsFlag")
        jsContentMap[targetJsFlag]?.let {
            if (!it.ready)
                it.initObject(wv.context)
            when (it) {
                is WvpmJsContentNormal -> eval(wv, it.content, callback)
                is WvpmJsContentNeedFormat -> eval(wv, it.formatContent(jsArgs), callback)
            }
        }
    }

    fun eval(wv: WebView, targetJsRaw: String?, callback: WvpmCallback?) {
        wv.evaluateJavascript(targetJsRaw.orEmpty(), ValueCallback {
            Log.d(TAG, "get response: $it")
            // todo move callback to core for task management
            callback?.run {
                return@ValueCallback callback(WvpmResponse(it))
            }
        })
    }
}
