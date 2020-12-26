# WVPM 

![jitpack-badge](https://jitpack.io/v/williamfzc/wvpm.svg?style=flat-square)

A lightweight performance monitor for webview in android, with kotlin.

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

Less brain fuck. You can collect/save/upload them as you wish.

Originally this tool was designed for working with apm systems. It should be extendable enough I think.

## further goals (working in progress)

- [x] Injecting some callback functions to webview events (e.g. onPageFinished)
- [x] Built-in js scripts for easily getting something from js side (e.g. window.performance.xxx)
- [ ] (Actually it can but I have no test) Working with ASM (dynamically applying this tool to all the webviews of your app)
- [x] Different kinds of webviews
- [x] Kotlin & Java usage

## full example

see [demo](./demo).

## installation

This repo uses [jitpack.io](jitpack.io).

root gradle:

```bash
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

app gradle:

```bash
dependencies {
    implementation 'com.github.williamfzc:wvpm:0.2.0'
}
```

## Why not Jsbridge?

Jsbridge is good in developping your own app. WVPM aims at debugging/test usage.

BTW, it's nearly deprecated.

## Inspired from ...

- [DoraemonKit](https://github.com/didi/DoraemonKit)
- [JsBridge](https://github.com/lzyzsd/JsBridge)
- ...

Thanks.

## License

[MIT](LICENSE)
