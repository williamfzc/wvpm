package com.github.williamfzc.wvpm.js

import android.util.Log
import android.webkit.JavascriptInterface
import com.github.williamfzc.wvpm.WvpmCallbackLocation
import com.github.williamfzc.wvpm.WvpmTaskManager

internal object WvpmJsInterface {
    private const val TAG = "WvpmJsInterface"
    const val JS_OBJECT_NAME = "wvpm"

    @JavascriptInterface
    fun response(taskId: String, msg: String) {
        Log.d(TAG, "task: $taskId, msg: $msg")
        WvpmTaskManager.getTask(taskId)?.run {
            this.applyCallback(WvpmCallbackLocation.FLAG_CB_LOCATION_JS, msg)
        }
    }
}
