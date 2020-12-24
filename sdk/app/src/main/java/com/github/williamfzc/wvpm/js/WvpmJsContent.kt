package com.github.williamfzc.wvpm.js

import android.content.Context
import android.util.Log
import java.io.FileNotFoundException

abstract class WvpmJsContent(
    private val path: String = "",
    var content: String = ""
) {
    open val TAG = "WvpmJsContent"

    val ready: Boolean
        get() = this.content.isNotEmpty()

    fun initObject(ctx: Context) {
        if (ready)
            return
        // no content now
        // normally load
        if (path.isNotEmpty()) {
            try {
                this.content = ctx.assets.open(path).readBytes().toString(Charsets.UTF_8)
                Log.d(TAG, "init js finished from: $path")
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
        } else {
            Log.e(TAG, "both path and content are empty")
        }
    }
}

internal object WvpmJsPerfTiming : WvpmJsContent(path = "wvpm_js/timing.js") {
    override val TAG: String
        get() = "WvpmJsPerfTiming"
}

internal object WvpmJsPerfNavigation : WvpmJsContent(path = "wvpm_js/navigation.js") {
    override val TAG: String
        get() = "WvpmJsPerfNavigation"
}

internal object WvpmJsDebugSayHi : WvpmJsContent(content = "console.log('hello world :)')") {
    override val TAG: String
        get() = "WvpmJsDebugSayHi"
}
