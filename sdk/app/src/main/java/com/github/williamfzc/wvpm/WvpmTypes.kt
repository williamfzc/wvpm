package com.github.williamfzc.wvpm

import java.util.*

internal typealias WvpmCallback = (WvpmResponse) -> Unit

class WvpmTask(
    val location: WvpmInjectLocationBase,
    val jsFlag: WvpmJsFlagBase,
    val callback: WvpmCallback?,
    val jsArgs: Array<String>? = null
) {
    val id = UUID.randomUUID()
}

interface WvpmInjectLocationBase

enum class WvpmInjectLocation: WvpmInjectLocationBase {
    FLAG_ON_PAGE_FINISHED,
    FLAG_ON_PAGE_STARTED,
}

interface WvpmJsFlagBase

enum class WvpmJsFlag: WvpmJsFlagBase {
    FLAG_JS_PERF_TIMING,
    FLAG_JS_PERF_NAVIGATION,
    FLAG_JS_DEBUG_SAY_HI,
    FLAG_JS_DEBUG_FORMAT,
}
