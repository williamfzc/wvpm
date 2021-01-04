package com.github.williamfzc.wvpm

internal typealias WvpmCallback = (WvpmResponse) -> Unit

interface WvpmInjectLocationBase

enum class WvpmInjectLocation : WvpmInjectLocationBase {
    FLAG_ON_PAGE_FINISHED,
    FLAG_ON_PAGE_STARTED,
    FLAG_ON_PAGE_COMMIT_VISIBLE,
    FLAG_ON_LOAD_RESOURCE,
    FLAG_ON_NOWHERE
}

interface WvpmJsFlagBase

enum class WvpmJsFlag : WvpmJsFlagBase {
    FLAG_JS_PERF_TIMING,
    FLAG_JS_PERF_NAVIGATION,
    FLAG_JS_PERF_ENTRIES,
    FLAG_JS_DEBUG_SAY_HI,
    FLAG_JS_DEBUG_FORMAT,
}

enum class WvpmJsInterfaceFlag : WvpmJsFlagBase {
    FLAG_JS_PERF_FPS,
    FLAG_JS_PERF_RENDER
}

interface WvpmCallbackLocationBase

enum class WvpmCallbackLocation : WvpmCallbackLocationBase {
    FLAG_CB_LOCATION_JS,
    FLAG_CB_LOCATION_ANDROID
}
