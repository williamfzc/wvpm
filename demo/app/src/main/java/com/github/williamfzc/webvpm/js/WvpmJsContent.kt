package com.github.williamfzc.webvpm.js

import android.content.Context
import android.util.Log
import java.io.FileNotFoundException

abstract class WvpmJsContent(path: String, ctx: Context?) {
    open val TAG = "WvpmJsContent"
    var content: String? = null
    val ready: Boolean
        get() = !this.content.isNullOrEmpty()

    init {
        ctx?.let {
            try {
                this.content = ctx.assets.open(path).readBytes().toString(Charsets.UTF_8)
                Log.d(TAG, "init js finished from: $path")
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
        } ?: run {
            Log.e(TAG, "context is null")
        }
    }
}

class PerfWvpmJsContent(path: String = "perf.js", ctx: Context?) : WvpmJsContent(path, ctx) {
    override val TAG: String
        get() = "PerfWvpmJsContent"
}
