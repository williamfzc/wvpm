package com.github.williamfzc.wvpm

import android.os.Build
import android.util.Log
import android.webkit.WebView
import com.github.williamfzc.wvpm.js.WvpmJsManager

// API always pack everything into a task
public object WvpmAPI {
    private val TAG = "WvpmAPI"

    @JvmStatic
    fun inject(
        wv: WebView?,
        task: WvpmTask
    ): WvpmTask {
        // required api 26 at least. see:
        // https://developer.android.com/reference/android/webkit/WebView#getWebViewClient()
        // https://github.com/didi/DoraemonKit/blob/master/Android/java/doraemonkit/src/main/java/com/didichuxing/doraemonkit/aop/WebViewHook.java
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            wv?.run {
                WvpmInject.inject(this, task)
            }
        else
            Log.w(TAG, "wvpm can not be used under api version 26")
        return task
    }

    @JvmStatic
    @JvmOverloads
    fun inject(
        wv: WebView?,
        targetJs: WvpmJsFlagBase,
        callback: WvpmCallback? = null,
        injectLocation: WvpmInjectLocationBase,
        jsArgs: Array<String>? = null
    ): WvpmTask = inject(wv, WvpmTask(injectLocation, targetJs, callback, jsArgs))

    @JvmStatic
    @JvmOverloads
    fun injectOnPageStarted(
        wv: WebView?,
        targetJs: WvpmJsFlagBase,
        callback: WvpmCallback? = null,
        jsArgs: Array<String>? = null
    ): WvpmTask =
        inject(wv, targetJs, callback, WvpmInjectLocation.FLAG_ON_PAGE_STARTED, jsArgs)

    @JvmStatic
    @JvmOverloads
    fun injectOnLoadResource(
        wv: WebView?,
        targetJs: WvpmJsFlagBase,
        callback: WvpmCallback? = null,
        jsArgs: Array<String>? = null
    ): WvpmTask =
        inject(wv, targetJs, callback, WvpmInjectLocation.FLAG_ON_LOAD_RESOURCE, jsArgs)

    @JvmStatic
    @JvmOverloads
    fun injectOnPageCommitVisible(
        wv: WebView?,
        targetJs: WvpmJsFlagBase,
        callback: WvpmCallback? = null,
        jsArgs: Array<String>? = null
    ): WvpmTask =
        inject(wv, targetJs, callback, WvpmInjectLocation.FLAG_ON_PAGE_COMMIT_VISIBLE, jsArgs)

    @JvmStatic
    @JvmOverloads
    fun injectOnPageFinished(
        wv: WebView?,
        targetJs: WvpmJsFlagBase,
        callback: WvpmCallback? = null,
        jsArgs: Array<String>? = null
    ): WvpmTask =
        inject(wv, targetJs, callback, WvpmInjectLocation.FLAG_ON_PAGE_FINISHED, jsArgs)

    @JvmStatic
    @JvmOverloads
    fun execInside(
        wv: WebView?,
        targetJs: WvpmJsFlagBase,
        callback: WvpmCallback? = null,
        jsArgs: Array<String>? = null
    ): WvpmTask {
        val task = WvpmTask(WvpmInjectLocation.FLAG_ON_NOWHERE, targetJs, callback, jsArgs)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            wv?.run {
                WvpmJsManager.eval(this, task)
            }
        else
            Log.w(TAG, "wvpm can not be used under api version 26")
        return task
    }

    @JvmStatic
    fun getPerfTiming(wv: WebView?, callback: WvpmCallback?): WvpmTask =
        execInside(wv, WvpmJsFlag.FLAG_JS_PERF_TIMING, callback)

    @JvmStatic
    fun getPerfNavigation(wv: WebView?, callback: WvpmCallback?): WvpmTask =
        execInside(wv, WvpmJsFlag.FLAG_JS_PERF_NAVIGATION, callback)

    @JvmStatic
    @JvmOverloads
    fun registerFpsMonitor(
        wv: WebView?,
        callback: WvpmCallback?,
        fpsThreshold: Int,
        once: Boolean = false
    ): WvpmTask {
        val task = WvpmTask(
            WvpmInjectLocation.FLAG_ON_PAGE_STARTED,
            WvpmJsInterfaceFlag.FLAG_JS_PERF_FPS,
            callback,
            arrayOf(fpsThreshold.toString()),
            once
        )
        inject(wv, task)
        return task
    }
}
