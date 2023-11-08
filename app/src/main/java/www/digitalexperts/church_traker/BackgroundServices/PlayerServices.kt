import android.app.Notification
import android.app.NotificationManager.IMPORTANCE_HIGH
import android.app.PendingIntent
import android.app.Service
import android.app.job.JobInfo.PRIORITY_MAX
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.media.session.MediaSession
import android.os.Binder
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.PlayerNotificationManager
import www.digitalexperts.church_traker.MainActivity
import www.digitalexperts.church_traker.R
import java.util.Random

class PlayerService : Service() {

    private val iBinder = ServiceBinder()
    var player: Player? = null
    private var mediaSession: MediaSession? = null
    lateinit var notificationManager: PlayerNotificationManager

    inner class ServiceBinder : Binder() {
        fun getPlayerService(): PlayerService = this@PlayerService
    }


    override fun onBind(intent: Intent?): IBinder {
        return iBinder
    }

    override fun onCreate() {
        super.onCreate()
        player = ExoPlayer.Builder(this).build()
        mediaSession =
            MediaSession.Builder(this, player!!).setSessionActivity(pendingIntent()!!)
                .setId(Random(5).toString())
                .build()
        notificationManager = PlayerNotificationManager.Builder(this, 111, "Music Channel")
            .setChannelImportance(IMPORTANCE_HIGH)
            .setSmallIconResourceId(R.drawable.dove)
            .setChannelDescriptionResourceId(R.string.app_name)
            .setChannelNameResourceId(R.string.app_name)
            .setMediaDescriptionAdapter(audioDescriptor)
            .setNotificationListener(notificationListener)
            .build()


        notificationManager.setPlayer(player)
        notificationManager.setPriority(NotificationCompat.PRIORITY_MAX)
        notificationManager.setUseRewindAction(true)
        notificationManager.setUseFastForwardAction(false)
        notificationManager.setUsePreviousAction(false)
        notificationManager.setUsePlayPauseActions(true)
    }


    override fun onDestroy() {
        if (player?.isPlaying!!) {
            player?.stop()
        }
        notificationManager.setPlayer(null)
        player?.release()
        player = null
        stopForeground(true)
        stopSelf()
        super.onDestroy()
    }

    private val notificationListener = object : PlayerNotificationManager.NotificationListener {
        override fun onNotificationCancelled(notificationId: Int, dismissedByUser: Boolean) {
            super.onNotificationCancelled(notificationId, dismissedByUser)
            stopForeground(true)
            if (player?.isPlaying!!) {
                player?.stop()
                player?.release()
            }
        }

        override fun onNotificationPosted(
            notificationId: Int,
            notification: Notification,
            ongoing: Boolean
        ) {
            super.onNotificationPosted(notificationId, notification, ongoing)
            startForeground(notificationId, notification)
        }
    }

    private val audioDescriptor = object : PlayerNotificationManager.MediaDescriptionAdapter {
        override fun getCurrentContentTitle(player: Player): CharSequence {
            return player.currentMediaItem?.mediaMetadata?.albumTitle!!
        }

        override fun createCurrentContentIntent(player: Player): PendingIntent? {
            return pendingIntent()
        }

        override fun getCurrentContentText(player: Player): CharSequence? {
            return ""
        }

        override fun getCurrentLargeIcon(
            player: Player,
            callback: PlayerNotificationManager.BitmapCallback
        ): Bitmap? {
            val bitmapDrawable: BitmapDrawable =
                ContextCompat.getDrawable(
                    applicationContext,
                    R.drawable.dove
                ) as BitmapDrawable
            return bitmapDrawable.bitmap
        }
    }

    private fun pendingIntent(): PendingIntent? {
        val intent = Intent(applicationContext, MainActivity::class.java)
        return PendingIntent.getActivity(
            applicationContext,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }
}