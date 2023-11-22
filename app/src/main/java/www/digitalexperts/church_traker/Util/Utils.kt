package www.digitalexperts.church_traker.Util

import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.core.net.toUri
import www.digitalexperts.church_traker.MainActivity
import java.io.IOException
import java.net.URL

fun Context.gotoscreen(screen:String): PendingIntent {
    val REQUEST_CODE=675646
    val taskDetailIntent = Intent(
        Intent.ACTION_VIEW,
        "https://www.digitalexperts.church_traker.Presentation.Dashboard/${screen}".toUri(),
        this,
        MainActivity::class.java
    )
    val pendingintent: PendingIntent = TaskStackBuilder.create(this).run {
        addNextIntentWithParentStack(taskDetailIntent)
        getPendingIntent(REQUEST_CODE, PendingIntent.FLAG_UPDATE_CURRENT)
    }

    return pendingintent
}
fun getBitmapfromUrl(imageUrl: String?): Bitmap? {
    return try {
        val url = URL(imageUrl)
        BitmapFactory.decodeStream(url.openConnection().getInputStream())
    } catch (e: IOException) {
        System.out.println(e)
        null
    }
}