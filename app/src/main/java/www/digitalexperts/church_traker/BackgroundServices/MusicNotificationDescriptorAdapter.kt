package www.digitalexperts.church_traker.BackgroundServices

import android.app.PendingIntent
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.core.content.ContextCompat
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.ui.PlayerNotificationManager
import www.digitalexperts.church_traker.R

@UnstableApi
class MusicNotificationDescriptorAdapter(
    private val context: Context,
    private val pendingIntent: PendingIntent?
) : PlayerNotificationManager.MediaDescriptionAdapter {
    override fun getCurrentContentTitle(player: Player): CharSequence ="JESUS IS LORD RADIO"
       /* player.mediaMetadata.albumTitle ?: "JESUS IS LORD RADIO"*/

    override fun createCurrentContentIntent(player: Player): PendingIntent? = pendingIntent

    override fun getCurrentContentText(player: Player): CharSequence =
        player.mediaMetadata.displayTitle ?: "Live Streaming"

    override fun getCurrentLargeIcon(
        player: Player,
        callback: PlayerNotificationManager.BitmapCallback
    ): Bitmap? {
        val bitmapDrawable: BitmapDrawable =
                ContextCompat.getDrawable(
                    context,
                    R.drawable.logo
                ) as BitmapDrawable

        return bitmapDrawable.bitmap
    }

}