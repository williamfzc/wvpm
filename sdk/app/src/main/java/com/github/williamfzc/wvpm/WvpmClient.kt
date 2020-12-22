package com.github.williamfzc.wvpm

import android.graphics.Bitmap
import android.net.http.SslError
import android.os.Build
import android.os.Message
import android.util.Log
import android.view.KeyEvent
import android.webkit.*
import androidx.annotation.RequiresApi
import com.github.williamfzc.wvpm.js.WvpmJsFlag


data class WvpmTask(val jsFlag: WvpmJsFlag, val callback: WvpmCallback?)

open class WvpmClient(
    private val originClient: WebViewClient?,
    private val hooks: Map<WvpmInjectLocation, List<WvpmTask>> = mapOf()
) : WebViewClient() {
    private val TAG = "WvpmClient"
    private var mHooks: MutableMap<WvpmInjectLocation, MutableList<WvpmTask>> = mutableMapOf()

    init {
        originClient?.let {
            if (it is WvpmClient) {
                this.mHooks = it.mHooks
                // avoid these hooks called recursively because of inherit
                it.mHooks = mutableMapOf()
            }
        }

        for ((k, v) in hooks) {
            mHooks[k]?.run {
                this.addAll(v)
            } ?: also {
                mHooks[k] = v.toMutableList()
            }
        }
        Log.d(TAG, "hooks: ${this.mHooks}")
    }

    // some special hooks
    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        originClient?.apply {
            this.onPageStarted(view, url, favicon)
        } ?: apply {
            this.onPageStarted(view, url, favicon)
        }

        // hooks
        mHooks[WvpmInjectLocation.FLAG_ON_PAGE_STARTED]?.let {
            for (each in it)
                WvpmCore.apply(view, url, each.jsFlag, each.callback)
        }
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        originClient?.apply {
            this.onPageFinished(view, url)
        } ?: apply {
            this.onPageFinished(view, url)
        }
        // hooks
        mHooks[WvpmInjectLocation.FLAG_ON_PAGE_FINISHED]?.let {
            for (each in it)
                WvpmCore.apply(view, url, each.jsFlag, each.callback)
        }
    }

    // comes from doraemonkit, thanks:
    // https://github.com/didi/DoraemonKit/blob/master/Android/java/doraemonkit/src/main/java/com/didichuxing/doraemonkit/kit/h5_help/DokitWebViewClient.kt
    @RequiresApi(Build.VERSION_CODES.N)
    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        if (originClient != null) {
            return originClient.shouldOverrideUrlLoading(view, request)
        }
        return super.shouldOverrideUrlLoading(view, request)
    }

    override fun shouldInterceptRequest(view: WebView?, url: String?): WebResourceResponse? {
        if (originClient != null) {
            return originClient.shouldInterceptRequest(view, url)
        }
        return super.shouldInterceptRequest(view, url)
    }

    override fun onLoadResource(view: WebView?, url: String?) {
        if (originClient != null) {
            return originClient.onLoadResource(view, url)
        }
        super.onLoadResource(view, url)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onPageCommitVisible(view: WebView?, url: String?) {
        if (originClient != null) {
            return originClient.onPageCommitVisible(view, url)
        }
        super.onPageCommitVisible(view, url)
    }

    override fun onTooManyRedirects(view: WebView?, cancelMsg: Message?, continueMsg: Message?) {
        if (originClient != null) {
            return originClient.onTooManyRedirects(view, cancelMsg, continueMsg)
        }
        super.onTooManyRedirects(view, cancelMsg, continueMsg)
    }

    override fun onReceivedError(
        view: WebView?,
        errorCode: Int,
        description: String?,
        failingUrl: String?
    ) {
        if (originClient != null) {
            return originClient.onReceivedError(view, errorCode, description, failingUrl)
        }
        super.onReceivedError(view, errorCode, description, failingUrl)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onReceivedError(
        view: WebView?,
        request: WebResourceRequest?,
        error: WebResourceError?
    ) {
        if (originClient != null) {
            return originClient.onReceivedError(view, request, error)
        }
        super.onReceivedError(view, request, error)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onReceivedHttpError(
        view: WebView?,
        request: WebResourceRequest?,
        errorResponse: WebResourceResponse?
    ) {
        if (originClient != null) {
            return originClient.onReceivedHttpError(view, request, errorResponse)
        }
        super.onReceivedHttpError(view, request, errorResponse)
    }

    override fun onFormResubmission(view: WebView?, dontResend: Message?, resend: Message?) {
        if (originClient != null) {
            return originClient.onFormResubmission(view, dontResend, resend)
        }
        super.onFormResubmission(view, dontResend, resend)
    }

    override fun doUpdateVisitedHistory(view: WebView?, url: String?, isReload: Boolean) {

        if (originClient != null) {
            return originClient.doUpdateVisitedHistory(view, url, isReload)
        }
        super.doUpdateVisitedHistory(view, url, isReload)
    }

    override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
        if (originClient != null) {
            return originClient.onReceivedSslError(view, handler, error)
        }
        super.onReceivedSslError(view, handler, error)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onReceivedClientCertRequest(view: WebView?, request: ClientCertRequest?) {
        if (originClient != null) {
            return originClient.onReceivedClientCertRequest(view, request)
        }
        super.onReceivedClientCertRequest(view, request)
    }

    override fun onReceivedHttpAuthRequest(
        view: WebView?,
        handler: HttpAuthHandler?,
        host: String?,
        realm: String?
    ) {
        if (originClient != null) {
            return originClient.onReceivedHttpAuthRequest(view, handler, host, realm)
        }
        super.onReceivedHttpAuthRequest(view, handler, host, realm)
    }

    override fun shouldOverrideKeyEvent(view: WebView?, event: KeyEvent?): Boolean {
        if (originClient != null) {
            return originClient.shouldOverrideKeyEvent(view, event)
        }
        return super.shouldOverrideKeyEvent(view, event)
    }

    override fun onUnhandledKeyEvent(view: WebView?, event: KeyEvent?) {
        if (originClient != null) {
            return originClient.onUnhandledKeyEvent(view, event)
        }
        super.onUnhandledKeyEvent(view, event)
    }

    override fun onScaleChanged(view: WebView?, oldScale: Float, newScale: Float) {
        if (originClient != null) {
            return originClient.onScaleChanged(view, oldScale, newScale)
        }
        super.onScaleChanged(view, oldScale, newScale)
    }

    override fun onReceivedLoginRequest(
        view: WebView?,
        realm: String?,
        account: String?,
        args: String?
    ) {
        if (originClient != null) {
            return originClient.onReceivedLoginRequest(view, realm, account, args)
        }
        super.onReceivedLoginRequest(view, realm, account, args)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onRenderProcessGone(view: WebView?, detail: RenderProcessGoneDetail?): Boolean {
        if (originClient != null) {
            return originClient.onRenderProcessGone(view, detail)
        }
        return super.onRenderProcessGone(view, detail)
    }

    @RequiresApi(Build.VERSION_CODES.O_MR1)
    override fun onSafeBrowsingHit(
        view: WebView?,
        request: WebResourceRequest?,
        threatType: Int,
        callback: SafeBrowsingResponse?
    ) {
        if (originClient != null) {
            return originClient.onSafeBrowsingHit(view, request, threatType, callback)
        }
        super.onSafeBrowsingHit(view, request, threatType, callback)
    }
}
