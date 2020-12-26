package com.github.williamfzc.wvpm.js

import android.content.Context
import android.util.Log
import java.io.FileNotFoundException

abstract class WvpmJsContent(
    private val path: String = "",
    open var content: String = ""
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

abstract class WvpmJsContentNormal(path: String = "", content: String = "") : WvpmJsContent(path, content)

abstract class WvpmJsContentNeedFormat(path: String = "", content: String = "") : WvpmJsContent(path, content) {
    fun formatContent(jsArgs: Array<String>?): String {
        // todo: kotlin does not have String template engine ..
        if (jsArgs == null)
            return content
        return String.format(content, *jsArgs)
    }
}

internal object WvpmJsPerfTiming : WvpmJsContentNormal(content = "return window.performance.timing.toJSON()") {
    override val TAG: String
        get() = "WvpmJsPerfTiming"
}

internal object WvpmJsPerfNavigation : WvpmJsContentNormal(content = "return window.performance.navigation.toJSON()") {
    override val TAG: String
        get() = "WvpmJsPerfNavigation"
}

internal object WvpmJsPerfFps : WvpmJsContentNeedFormat(path = "wvpm_js/perf_fps.js") {
    override val TAG: String
        get() = "WvpmJsPerfFps"
}

internal object WvpmJsDebugSayHi : WvpmJsContentNormal(content = "console.log('hello world :)')") {
    override val TAG: String
        get() = "WvpmJsDebugSayHi"
}

internal object WvpmJsDebugFormat : WvpmJsContentNeedFormat(content = "console.log('%s :)')") {
    override val TAG: String
        get() = "WvpmJsDebugFormat"
}
