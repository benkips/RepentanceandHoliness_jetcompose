package www.digitalexperts.church_traker.Presentation.Dashboard

import android.graphics.Bitmap
import android.util.Log
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
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
import www.digitalexperts.church_traker.Util.FullScreenProgressbar
import www.digitalexperts.church_traker.Viewmodels.Folderviewmodel

@Composable
fun Webinfo(viewModel: Folderviewmodel){
    var toloadUrl:String=viewModel.foldername.collectAsState().value
    var mystringUrls:String
    val visibility = remember { mutableStateOf(false)}
    Log.d("Webinfo", "Webinfo: " + toloadUrl)
    if (toloadUrl.contains("Video"))
        mystringUrls="https://repentanceandholinessinfo.com/videoteachings.php"
    else {
        mystringUrls="https://repentanceandholinessinfo.com/auditeachings.php"
    }
/*    val webViewState = rememberWebViewState(url = mystringUrls)
    val mContext = LocalContext.current
    val loadstate=webViewState.loadingState*/

  /*  if (visibility.value){
        Dialog(
            onDismissRequest = {
                visibility.value = false
            },
            properties = DialogProperties(
                usePlatformDefaultWidth = false // experimental
            )
        ) {
            Surface(modifier = Modifier.fillMaxSize()) {

                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {


                    Image(
                        painter = painterResource(id = R.drawable.dove),
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .height(200.dp)
                            .fillMaxWidth(),

                        )

                    Spacer(modifier = Modifier.height(20.dp))
                    //.........................Text: title
                    Text(
                        text = "Loading...",
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(top = 20.dp)
                            .fillMaxWidth(),
                        letterSpacing = 2.sp,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.primary,
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    //.........................Text : description
                    Text(
                        text = "Please wait",
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(top = 10.dp, start = 25.dp, end = 25.dp)
                            .fillMaxWidth(),
                        letterSpacing = 3.sp,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.primary,
                    )
                    //.........................Spacer
                    Spacer(modifier = Modifier.height(24.dp))

                }

            }
        }
    }*/








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
                if (visibility.value){
                    //LinearProgressIndicator()
                    FullScreenProgressbar()
                }


                //visibility.value = loadstate is LoadingState.Loading

/*                WebView(
                    modifier = Modifier.fillMaxSize(),
                    state = webViewState,
                    captureBackPresses = true,
                    onCreated = { it : WebView ->
                        it.settings.javaScriptEnabled = true
                        it.setDownloadListener(
                            object : DownloadListener {
                                override fun onDownloadStart(
                                    url: String?,
                                    userAgent: String?,
                                    contentDisposition: String?,
                                    mimetype: String?,
                                    contentLength: Long
                                ) {
                                    val request: DownloadManager.Request =
                                        DownloadManager.Request(Uri.parse(url))
                                    request.apply {
                                        setMimeType(mimetype)
                                        setDescription("Downloading file")
                                        addRequestHeader("UserAgent", userAgent)
                                        setTitle(
                                            URLUtil.guessFileName(
                                                url,
                                                contentDisposition,
                                                mimetype
                                            )
                                        )
                                        setNotificationVisibility(
                                            DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED
                                        )
                                    }
                                    val downloadManager =mContext.getSystemService(
                                        Context.DOWNLOAD_SERVICE
                                    ) as DownloadManager?
                                    downloadManager?.enqueue(request)
                                    Toast.makeText(mContext, "Downloading file", Toast.LENGTH_LONG)
                                        .show()
                                }
                            }
                        )
                    }
                )*/


                AndroidView(
                    modifier = Modifier.fillMaxSize(),
                    factory = { context ->

                        WebView(context).apply {
                            layoutParams = ViewGroup.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT
                            )}
                        WebviewHolder(context = context).apply {

                            var webViewClient: WebViewClient = object : WebViewClient() {
                                override fun onReceivedError(
                                    view: WebView?,
                                    errorCode: Int,
                                    description: String?,
                                    failingUrl: String?
                                ) {
                                    super.onReceivedError(view, errorCode, description, failingUrl)
                                    visibility.value = false
                                    mystringUrls = "file:///android_asset/android/errorpage.html";
                                    webview.loadUrl(mystringUrls )

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
                            loadUrl(mystringUrls)


                        }.webview
                    },
                    update = {
                    })

            }
        }
    }
}