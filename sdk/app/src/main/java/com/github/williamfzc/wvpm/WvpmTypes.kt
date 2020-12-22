// these tasks should be exposed
package com.github.williamfzc.wvpm

public typealias WvpmCallback = (WvpmResponse) -> Unit

public data class WvpmTask(
    val location: WvpmInjectLocation,
    val jsFlag: WvpmJsFlag,
    val callback: WvpmCallback?
)

public enum class WvpmInjectLocation {
    FLAG_ON_PAGE_FINISHED,
    FLAG_ON_PAGE_STARTED,
}

public enum class WvpmJsFlag {
    FLAG_JS_PERF_TIMING,
    FLAG_JS_PERF_NAVIGATION
}
