package com.github.williamfzc.webvpm_demo

import android.os.Build
import kotlinx.android.synthetic.main.activity_main.*
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config


@Config(sdk = [Build.VERSION_CODES.P])
@RunWith(RobolectricTestRunner::class)
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        val activity: MainActivity = Robolectric.setupActivity(MainActivity::class.java)
        Thread.sleep(5000)

        val clientName = activity.mWebview.webViewClient::class.qualifiedName
        assertNotNull(clientName)
        assertTrue(clientName?.contains("WvpmClient")!!)
        activity.button.performClick()
        Thread.sleep(5000)
    }
}
