package com.github.williamfzc.wvpm

import java.util.*

class WvpmTask(
    val location: WvpmInjectLocationBase,
    val jsFlag: WvpmJsFlagBase,
    private val callback: WvpmCallback?,
    val jsArgs: Array<String>? = null,
    private val autoRemove: Boolean = true
) {
    val id = UUID.randomUUID().toString()
    private val isJsCallback = jsFlag is WvpmJsInterfaceFlag

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
        applyCallback(msg)
        if (!autoRemove && !isJsCallback)
            WvpmTaskManager.removeTask(id)
    }

    private fun applyCallbackJs(msg: String) {
        applyCallback(msg)
        if (!autoRemove && isJsCallback)
            WvpmTaskManager.removeTask(id)
    }

    private fun applyCallback(msg: String) {
        callback?.run {
            WvpmResponse(msg)
        }
    }
}

// todo: cross processing ?
// todo: performance ?
internal object WvpmTaskManager {
    private val dataMap = mutableMapOf<String, WvpmTask>()

    fun addTask(newTask: WvpmTask) {
        dataMap[newTask.id] = newTask
    }

    fun removeTask(taskId: String) {
        dataMap.remove(taskId)
    }

    fun getTask(taskId: String): WvpmTask? {
        return dataMap[taskId]
    }
}
