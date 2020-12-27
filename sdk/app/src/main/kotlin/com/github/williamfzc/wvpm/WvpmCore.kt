package com.github.williamfzc.wvpm

import android.util.Log
import android.webkit.WebView
import com.github.williamfzc.wvpm.js.WvpmJsManager

internal object WvpmCore {
    private val TAG = "WvpmCore"

    // TODO: not every hook function has the same arguments (e.g. url
    // https://github.com/williamfzc/wvpm/issues/12
    fun applyTask(view: WebView?, url: String?, task: WvpmTask) {
        Log.d(TAG, "applying task on ${task.location}, ${task.jsFlag}, url: $url")
        view?.run {
            task.url = url
            WvpmTaskManager.addTask(task)
            WvpmJsManager.eval(this, task)
        }
    }

    fun applyTask(view: WebView?, url: String?, tasks: Iterable<WvpmTask>) {
        for (each in tasks)
            applyTask(view, url, each)
    }
}
