# WVPM 

An lightweight injector for webview in android, with kotlin.

## TL;DR

This tool provided series of js scripts with their flags. You can easily use them on android side:

```kotlin
WvpmAPI.injectOnPageFinished(
    mWebview,
    WvpmJsFlag.FLAG_JS_PERF_TIMING,
    fun(resp: WvpmResponse) {
        Log.d(TAG, "get js return after page finished in activity: ${resp.data}")
    }
)
// or other events too, e.g. onPageStarted ...
```

and get its return value in callback function:

```javascript
D/MainActivity: get js return after page finished in activity: {"connectEnd":1.608735286902e+12,"connectStart":1.608735286348e+12,"domComplete":1.608735311779e+12,"domContentLoadedEventEnd":1.608735295159e+12, ...
```

Less brain fuck. Originally this tool was designed for working with apm systems.

## goals

- Injecting some callback functions to webview events (e.g. onPageFinished)
- Built-in js scripts for easily getting something from js side (e.g. window.performance.xxx)
- Working with ASM (dynamically applying this tool to all the webviews of your app)
- Different kinds of webviews
- Kotlin & Java usage

## Why not Jsbridge?

Jsbridge is good in developping your own app. WVPM aims at debugging/test usage.

## License

[MIT](LICENSE)
