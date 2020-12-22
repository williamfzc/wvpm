package com.github.williamfzc.wvpm

import android.util.Log
import android.webkit.WebView
import com.github.williamfzc.wvpm.js.WvpmJsManager


object WvpmCore {
    private val TAG = "WvpmCore"

    fun applyTask(view: WebView?, url: String?, task: WvpmTask) {
        Log.d(TAG, "applying task on ${task.location}, ${task.jsFlag}, url: $url")
        view?.run {
            WvpmJsManager.eval(this, task.jsFlag, task.callback)
        }
    }

    fun applyTask(view: WebView?, url: String?, tasks: Iterable<WvpmTask>) {
        for (each in tasks)
            applyTask(view, url, each)
    }
}
