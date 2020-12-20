package com.github.williamfzc.webvpm

import android.content.Context
import android.util.Log
import java.io.FileNotFoundException

abstract class JsContent(path: String, ctx: Context?) {
    private val TAG = "JsContent"
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
        }
    }
}

class PerfJsContent(path: String = "perf.js", ctx: Context?) : JsContent(path, ctx)

object WvpmJsManager {
    private val TAG = "WvpmJsManager"
    private lateinit var jsSet: Set<JsContent>
    private var ready = false
    lateinit var jsPerf: PerfJsContent

    fun getInst(ctx: Context?): WvpmJsManager? {
        if (ready)
            return this

        return initInst(ctx)
    }

    private fun initInst(ctx: Context?): WvpmJsManager? {
        jsPerf = PerfJsContent(ctx = ctx)
        // others?
        jsSet = setOf<JsContent>(
            jsPerf
        )

        // check
        for (each in jsSet) {
            // already init but failed
            if (!each.ready) {
                Log.d(TAG, "something wrong when init $each")
                return null
            }
        }
        ready = true
        Log.d(TAG, "init inst ready")
        return this
    }
}
