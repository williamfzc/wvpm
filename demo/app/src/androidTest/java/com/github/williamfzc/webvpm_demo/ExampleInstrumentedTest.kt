package com.github.williamfzc.webvpm_demo

import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Test
import org.junit.runner.RunWith
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.test.core.app.ApplicationProvider
import com.github.williamfzc.wvpm.WvpmAPI
import com.github.williamfzc.wvpm.WvpmJsFlag
import com.github.williamfzc.wvpm.WvpmJsFlagBase
import com.github.williamfzc.wvpm.WvpmResponse
import com.github.williamfzc.wvpm.js.WvpmJsContent
import com.github.williamfzc.wvpm.js.WvpmJsManager

import org.junit.Before

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    lateinit var targetWebview: WebView
    val TAG = "androidTest"
    val URL = "http://baidu.com"

    // custom hook if you need
    enum class MyJsFlag : WvpmJsFlagBase { FLAG_NEW }

    val MyJsContent = object : WvpmJsContent(path = "perf.js") {}

    @Before
    fun prepare() {
        InstrumentationRegistry.getInstrumentation().runOnMainSync {
            targetWebview = WebView(ApplicationProvider.getApplicationContext())
        }
    }

    @Test
    fun testInject() {
        InstrumentationRegistry.getInstrumentation().runOnMainSync {
            // if we already have a client
            targetWebview.webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    Log.d(TAG, "origin page finished!")
                }
            }

            // custom hook
            WvpmJsManager.addJs(MainActivity.MyJsFlag.FLAG_NEW, MyJsContent)
            WvpmAPI.injectOnPageFinished(targetWebview, MainActivity.MyJsFlag.FLAG_NEW) {
                Log.d(TAG, "custom js: ${it.data}")
            }

            WvpmAPI.injectOnPageFinished(
                targetWebview,
                WvpmJsFlag.FLAG_JS_PERF_TIMING,
                fun(resp: WvpmResponse) {
                    Log.d(TAG, "get js return after page finished in activity: ${resp.data}")
                })
            WvpmAPI.injectOnPageFinished(
                targetWebview,
                WvpmJsFlag.FLAG_JS_PERF_NAVIGATION,
                fun(resp: WvpmResponse) {
                    Log.d(TAG, "again: get js return after page finished in activity: ${resp.data}")
                })
            WvpmAPI.injectOnPageStarted(
                targetWebview,
                WvpmJsFlag.FLAG_JS_PERF_TIMING,
                fun(resp: WvpmResponse) {
                    Log.d(TAG, "get js return before page started in activity: ${resp.data}")
                })
            targetWebview.loadUrl(URL)

            // wait for callback finished
            Thread.sleep(5000)
        }
    }
}
