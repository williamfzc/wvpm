package com.github.williamfzc.wvpm

import android.os.Build
import android.util.Log
import android.webkit.WebView
import com.github.williamfzc.wvpm.js.WvpmJsManager

public object WvpmAPI {
    private val TAG = "WvpmAPI"

    @JvmStatic
    fun inject(
        wv: WebView?,
        task: WvpmTask
    ) {
        // required api 26 at least. see:
        // https://developer.android.com/reference/android/webkit/WebView#getWebViewClient()
        // https://github.com/didi/DoraemonKit/blob/master/Android/java/doraemonkit/src/main/java/com/didichuxing/doraemonkit/aop/WebViewHook.java
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            wv?.run {
                WvpmInject.inject(this, task)
            }
        else
            Log.w(TAG, "wvpm can not be used under api version 26")
    }

    @JvmStatic
    fun inject(
        wv: WebView?,
        targetJs: WvpmJsFlag,
        callback: WvpmCallback? = null,
        injectLocation: WvpmInjectLocation
    ) = inject(wv, WvpmTask(injectLocation, targetJs, callback))

    @JvmStatic
    fun injectOnPageStarted(wv: WebView?, targetJs: WvpmJsFlag, callback: WvpmCallback? = null) =
        inject(wv, targetJs, callback, WvpmInjectLocation.FLAG_ON_PAGE_STARTED)

    @JvmStatic
    fun injectOnPageFinished(wv: WebView?, targetJs: WvpmJsFlag, callback: WvpmCallback? = null) =
        inject(wv, targetJs, callback, WvpmInjectLocation.FLAG_ON_PAGE_FINISHED)

    @JvmStatic
    fun execInside(wv: WebView?, targetJs: WvpmJsFlag, callback: WvpmCallback? = null) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            wv?.run {
                WvpmJsManager.eval(this, targetJs, callback)
            }
        else
            Log.w(TAG, "wvpm can not be used under api version 26")
    }

    @JvmStatic
    fun getPerfTiming(wv: WebView?, callback: WvpmCallback?) =
        execInside(wv, WvpmJsFlag.FLAG_JS_PERF, callback)
}
