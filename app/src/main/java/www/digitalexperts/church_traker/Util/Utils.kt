package www.digitalexperts.church_traker.Util

import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import androidx.core.net.toUri
import www.digitalexperts.church_traker.MainActivity

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