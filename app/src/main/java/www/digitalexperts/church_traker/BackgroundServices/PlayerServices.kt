package www.digitalexperts.church_traker.BackgroundServices

import android.app.Notification
import android.app.NotificationManager.IMPORTANCE_HIGH
import android.app.PendingIntent
import android.app.Service
import android.app.job.JobInfo.PRIORITY_MAX
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import  androidx.media3.session.MediaSession
import android.os.Binder
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerNotificationManager

import www.digitalexperts.church_traker.MainActivity
import www.digitalexperts.church_traker.R
import java.util.Random

@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
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
        /*notificationManager.setPriority(NotificationCompat.PRIORITY_MAX)*/
        notificationManager.setUseRewindAction(false )
        notificationManager.setUseFastForwardAction(false)
        notificationManager.setUsePreviousAction(false)
        notificationManager.setUsePlayPauseActions(true)
        notificationManager.setUseStopAction(true)
        notificationManager.setPriority(NotificationCompat.PRIORITY_DEFAULT)

    }


    override fun onDestroy() {
        if (player?.isPlaying!!) {
            player?.stop()
        }
        notificationManager.setPlayer(null)
        player?.release()
        player = null
        mediaSession?.run {
            player.release()
            release()
            mediaSession = null
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            stopForeground(STOP_FOREGROUND_DETACH)
        }
        stopSelf()
        super.onDestroy()
    }

    private val notificationListener = object : PlayerNotificationManager.NotificationListener {
        override fun onNotificationCancelled(notificationId: Int, dismissedByUser: Boolean) {
            super.onNotificationCancelled(notificationId, dismissedByUser)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                stopForeground(STOP_FOREGROUND_DETACH)
            }
           /* if (player?.isPlaying!!) {
                player?.stop()
                player?.release()
            }*/
            mediaSession?.run {
                player.release()
                release()
                mediaSession = null
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
            return "JESUS IS LORD  Radio"
        }

        override fun createCurrentContentIntent(player: Player): PendingIntent? {
            return pendingIntent()
        }

        override fun getCurrentContentText(player: Player): CharSequence? {
            return "Streaming Live"
        }

        override fun getCurrentLargeIcon(
            player: Player,
            callback: PlayerNotificationManager.BitmapCallback
        ): Bitmap? {
            /*val bitmapDrawable: BitmapDrawable =
                ContextCompat.getDrawable(
                    applicationContext,
                    R.drawable.logo
                ) as BitmapDrawable*/
            return null
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