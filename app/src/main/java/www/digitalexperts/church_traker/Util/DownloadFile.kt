package www.digitalexperts.church_traker.Util

import android.app.DownloadManager
import android.content.Context
import android.os.Build
import android.os.Environment
import androidx.annotation.RequiresApi
import androidx.core.net.toUri

class DownloadFile(private val context:Context) {
    @RequiresApi(Build.VERSION_CODES.M)
    private val downloadManager=context.getSystemService(DownloadManager::class.java)
    @RequiresApi(Build.VERSION_CODES.M)
    fun downloadFile(url:String):Long{

        val request=DownloadManager.Request(url.toUri()).apply {
            setMimeType("video/mp4")
            setNotificationVisibility(
                DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED
            )
            val randomNumber = (0..10000).random()
            setTitle("vid"+randomNumber+".mp4")
            setDestinationInExternalFilesDir(context,Environment.DIRECTORY_DOWNLOADS,"vid"+randomNumber+".mp4")
        }
        return downloadManager.enqueue(request)

    }
    @RequiresApi(Build.VERSION_CODES.M)
    fun downloadFilepdf(url:String):Long{

        val request=DownloadManager.Request(url.toUri()).apply {
            setMimeType("application/pdf")
            setNotificationVisibility(
                DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED
            )
            val randomNumber = (0..10000).random()
            setTitle("pdf"+randomNumber+".pdf")
            setDestinationInExternalFilesDir(context,Environment.DIRECTORY_DOWNLOADS,"vid"+randomNumber+".mp4")
        }
        return downloadManager.enqueue(request)

    }
}