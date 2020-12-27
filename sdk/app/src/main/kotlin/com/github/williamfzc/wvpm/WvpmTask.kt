package com.github.williamfzc.wvpm

import android.util.Log
import java.util.*

class WvpmTask(
    val location: WvpmInjectLocationBase,
    val jsFlag: WvpmJsFlagBase,
    private val callback: WvpmCallback?,
    val jsArgs: Array<String>? = null,
    private val autoRemove: Boolean = true
) {
    private val TAG = "WvpmTask"
    val id = UUID.randomUUID().toString()
    private val isJsCallback = jsFlag is WvpmJsInterfaceFlag

    // extra fields from client hooks
    var url: String? = null

    fun cancel() {
        WvpmTaskManager.removeTask(id)
    }

    fun applyCallback(from: WvpmCallbackLocationBase, msg: String) {
        when (from) {
            WvpmCallbackLocation.FLAG_CB_LOCATION_JS -> applyCallbackJs(msg)
            WvpmCallbackLocation.FLAG_CB_LOCATION_ANDROID -> applyCallbackAndroid(msg)
        }
    }

    private fun applyCallbackAndroid(msg: String) {
        Log.d(TAG, "apply calling from android")
        applyCallback(msg)
        if (autoRemove && !isJsCallback)
            WvpmTaskManager.removeTask(id)
    }

    private fun applyCallbackJs(msg: String) {
        Log.d(TAG, "apply calling from js")
        applyCallback(msg)
        if (autoRemove && isJsCallback)
            WvpmTaskManager.removeTask(id)
    }

    private fun applyCallback(msg: String) {
        callback?.let { cb ->
            cb(WvpmResponse(msg, this))
        }
    }
}

// todo: cross processing ?
// todo: performance ?
internal object WvpmTaskManager {
    private const val TAG = "WvpmTaskManager"
    private val dataMap = mutableMapOf<String, WvpmTask>()

    fun addTask(newTask: WvpmTask) {
        dataMap[newTask.id] = newTask
    }

    fun removeTask(taskId: String) {
        dataMap.remove(taskId)
    }

    fun getTask(taskId: String): WvpmTask? {
        if (!dataMap.containsKey(taskId)) {
            Log.w(TAG, "task $taskId not existed")
            return null
        }
        return dataMap[taskId]
    }
}
