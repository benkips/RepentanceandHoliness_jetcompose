package www.digitalexperts.church_tracker.Presentation.Dashhboard

import android.graphics.Bitmap
import android.util.Log
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.viewinterop.AndroidView
import www.digitalexperts.church_tracker.Util.FullScreenProgressbar
import www.digitalexperts.church_tracker.Viewmodels.Churchviewmodel
import www.digitalexperts.church_tracker.Viewmodels.Folderviewmodel

@Composable
fun Webinfo(viewModel: Folderviewmodel){
    var toloadUrl:String=viewModel.foldername.collectAsState().value
    var mystringUrls:String
    val visibility = remember { mutableStateOf(false)}

    Log.d("Webinfo", "Webinfo: " + toloadUrl)
    if (toloadUrl.contains("Video"))
        mystringUrls="https://repentanceandholinessinfo.com/videoteachings.php"
    else{
        mystringUrls="https://repentanceandholinessinfo.com/auditeachings.php"
    }

    if (visibility.value){
        FullScreenProgressbar()
    }

    Surface(
        color = Color.White
    ) {
        Box (
            Modifier.fillMaxSize()
        ){
            Column (
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .verticalScroll(rememberScrollState())

            ) {
                AndroidView(
                    modifier = Modifier.fillMaxSize(),
                    factory = { context ->
                        WebviewHolder(context = context).apply {
                            loadUrl(mystringUrls)
                            var webViewClient: WebViewClient = object : WebViewClient() {
                                override fun onReceivedError(
                                    view: WebView?,
                                    request: WebResourceRequest?,
                                    error: WebResourceError?
                                ) {
                                    super.onReceivedError(view, request, error)

                                }

                                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                                    super.onPageStarted(view, url, favicon)
                                    visibility.value = true

                                }

                                override fun onPageFinished(view: WebView?, url: String?) {
                                    super.onPageFinished(view, url)
                                    visibility.value = false
                                }
                            }

                            webview.webViewClient = webViewClient

                        }.webview
                    },
                    update = { })

            }
        }
    }
}