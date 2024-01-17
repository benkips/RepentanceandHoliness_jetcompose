package www.digitalexperts.church_traker.Presentation.Dashboard

import android.app.DownloadManager
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.os.Message
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.webkit.ConsoleMessage
import android.webkit.CookieManager
import android.webkit.DownloadListener
import android.webkit.URLUtil
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast


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

        webview.setDownloadListener(object : DownloadListener {
            override fun onDownloadStart(
                url: String, userAgent: String,
                contentDisposition: String, mimetype: String,
                contentLength: Long
            ) {

                //getting file name from url
                val filename = URLUtil.guessFileName(url, contentDisposition, mimetype)
                //DownloadManager.Request created with url.
                val request = DownloadManager.Request(Uri.parse(url))
                //cookie
                val cookie = CookieManager.getInstance().getCookie(url)
                //Add cookie and User-Agent to request
                request.addRequestHeader("Cookie", cookie)
                request.addRequestHeader("User-Agent", userAgent)
                //file scanned by MediaScannar
                request.allowScanningByMediaScanner()
                request.setDescription("Download file...")
                //Download is visible and its progress, after completion too.
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                //DownloadManager created
                val downloadmanager =
                    context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
                //Saving file in Download folder
                request.setDestinationInExternalPublicDir(
                    Environment.DIRECTORY_DOWNLOADS,
                    filename
                )
                //download enqued
                downloadmanager.enqueue(request)

                Toast.makeText(context, "Downloading file", Toast.LENGTH_SHORT).show()
            }

        })

        initWebview()

    }

    private fun initWebview() {
        val webSettings = webview.settings
        webSettings.javaScriptEnabled = true
        setupwebclient()
        setupchromeclient()

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