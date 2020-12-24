package com.github.williamfzc.wvpm

internal typealias WvpmCallback = (WvpmResponse) -> Unit

data class WvpmTask(
    val location: WvpmInjectLocationBase,
    val jsFlag: WvpmJsFlagBase,
    val callback: WvpmCallback?
)

interface WvpmInjectLocationBase

enum class WvpmInjectLocation: WvpmInjectLocationBase {
    FLAG_ON_PAGE_FINISHED,
    FLAG_ON_PAGE_STARTED,
}

interface WvpmJsFlagBase

enum class WvpmJsFlag: WvpmJsFlagBase {
    FLAG_JS_PERF_TIMING,
    FLAG_JS_PERF_NAVIGATION
}
