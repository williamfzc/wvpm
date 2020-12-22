package com.github.williamfzc.wvpm.js

import android.content.Context
import android.util.Log
import java.io.FileNotFoundException

abstract class WvpmJsContent(private val path: String) {
    open val TAG = "WvpmJsContent"

    var content: String? = null
    val ready: Boolean
        get() = !this.content.isNullOrEmpty()

    fun initObject(ctx: Context?) {
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

object PerfWvpmJsContent: WvpmJsContent(path = "wvpm_js/perf.js") {
    override val TAG: String
        get() = "PerfWvpmJsContent"
}
