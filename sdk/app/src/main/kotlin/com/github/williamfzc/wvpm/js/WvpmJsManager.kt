package com.github.williamfzc.wvpm.js

import android.os.Handler
import android.os.Looper
import android.util.Log
import android.webkit.ValueCallback
import android.webkit.WebView
import com.github.williamfzc.wvpm.*


object WvpmJsManager {
    private const val TAG = "WvpmJsManager"
    private var jsContentMap = mutableMapOf<WvpmJsFlagBase, WvpmJsContent>(
        WvpmJsFlag.FLAG_JS_PERF_TIMING to WvpmJsPerfTiming,
        WvpmJsFlag.FLAG_JS_PERF_NAVIGATION to WvpmJsPerfNavigation,
        WvpmJsFlag.FLAG_JS_DEBUG_SAY_HI to WvpmJsDebugSayHi,
        WvpmJsFlag.FLAG_JS_DEBUG_FORMAT to WvpmJsDebugFormat,
        WvpmJsInterfaceFlag.FLAG_JS_PERF_FPS to WvpmJsPerfFps,
        WvpmJsInterfaceFlag.FLAG_JS_PERF_RENDER to WvpmJsPerfRender
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

    fun eval(wv: WebView, task: WvpmTask) {
        Log.d(TAG, "trying to eval: ${task.id} ${task.jsFlag}")
        jsContentMap[task.jsFlag]?.let {
            if (!it.ready)
                it.initObject(wv.context)
            when (it) {
                is WvpmJsContentNormal -> eval(wv, task, it.content)
                is WvpmJsContentNeedFormat -> eval(wv, task, it.formatContent(task.jsArgs))
            }
        }
    }

    private fun eval(wv: WebView, task: WvpmTask, targetJsRaw: String?) {
        // `evaluateJavascript` only can be executed in main thread
        val realJs = wrapJs(task.id, targetJsRaw.orEmpty())
        Log.d(TAG, "eval task: ${task.id}, ${task.location}, ${task.jsFlag}, real js: $realJs")
        Handler(Looper.getMainLooper()).post {
            wv.evaluateJavascript(realJs, ValueCallback {
                Log.d(TAG, "get response from evaluateJs callback: $it")
                return@ValueCallback task.applyCallback(
                    WvpmCallbackLocation.FLAG_CB_LOCATION_ANDROID,
                    it
                )
            })
        }
    }

    private fun wrapJs(taskId: String, originJs: String): String {
        return "(function() { console.log('task started in js: $taskId');\n let taskId = '$taskId';\n ${originJs}})();"
    }
}
