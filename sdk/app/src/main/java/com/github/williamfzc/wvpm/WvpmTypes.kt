// these tasks should be exposed
package com.github.williamfzc.wvpm

typealias WvpmCallback = (WvpmResponse) -> Unit

data class WvpmTask(
    val location: WvpmInjectLocation,
    val jsFlag: WvpmJsFlagBase,
    val callback: WvpmCallback?
)

enum class WvpmInjectLocation {
    FLAG_ON_PAGE_FINISHED,
    FLAG_ON_PAGE_STARTED,
}

interface WvpmJsFlagBase

enum class WvpmJsFlag: WvpmJsFlagBase {
    FLAG_JS_PERF_TIMING,
    FLAG_JS_PERF_NAVIGATION
}
