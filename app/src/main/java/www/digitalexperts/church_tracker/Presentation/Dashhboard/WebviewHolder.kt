package www.digitalexperts.church_tracker.Presentation.Dashhboard

import android.content.Context
import android.graphics.Bitmap
import android.os.Message
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.webkit.ConsoleMessage
import android.webkit.WebChromeClient
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.FrameLayout
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import www.digitalexperts.church_tracker.Util.FullScreenProgressbar


class WebviewHolder(context: Context) {

    var webview: WebView = WebView(context)


    private var webChromeClient: WebChromeClient = object : WebChromeClient() {
        override fun onCreateWindow(
            view: WebView?,
            isDialog: Boolean,
            isUserGesture: Boolean,
            resultMsg: Message?
        ): Boolean {
            return super.onCreateWindow(view, isDialog, isUserGesture, resultMsg)
        }

        override fun onConsoleMessage(consoleMessage: ConsoleMessage?): Boolean {
            return super.onConsoleMessage(consoleMessage)
        }

    }


    private var webViewClient: WebViewClient = object : WebViewClient() {
        override fun onReceivedError(
            view: WebView?,
            errorCode: Int,
            description: String?,
            failingUrl: String?
        ) {
            val myerrorpage = "file:///android_asset/android/errorpage.html";
            webview.loadUrl(myerrorpage)

        }



        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?
        ): Boolean {
            return super.shouldOverrideUrlLoading(view, request)
        }

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {

            super.onPageStarted(view, url, favicon)
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
        }
    }

    init {
        webview.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        webview.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK && event.action == MotionEvent.ACTION_UP && webview.canGoBack()) {
                webview.goBack()
                return@OnKeyListener true
            }
            false
        })
        initWebview()
    }

    private fun initWebview() {
        val webSettings = webview.settings
        webSettings.javaScriptEnabled = true
        setupchromeclient()
        setupwebclient()
    }

    private fun setupchromeclient() {
        webview.webChromeClient = webChromeClient
    }

    private fun setupwebclient() {
        webview.webViewClient = webViewClient
    }

    fun loadUrl(Url: String) {
        webview.loadUrl(Url)
    }

    fun reload() {
        webview.reload()
    }
}