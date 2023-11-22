package www.digitalexperts.church_traker.Notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_DEFAULT
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import www.digitalexperts.church_traker.MainActivity
import www.digitalexperts.church_traker.R
import www.digitalexperts.church_traker.Util.Constants
import www.digitalexperts.church_traker.Util.getBitmapfromUrl
import www.digitalexperts.church_traker.Util.gotoscreen
import kotlin.random.Random
private const val CHANNEL_ID="mychannel"
class FirebaseService: FirebaseMessagingService() {


    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        val intent= Intent(this, MainActivity::class.java)
        val notificationmanager=getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationID= Random.nextInt()


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationchannnel(notificationmanager)
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        if(message.data.get("image").equals("none")){

            val pendingIntent=gotoscreen(Constants.Screens.HEALING_SCREEN)
            val notification=wordnotification(pendingIntent,message.data.get("title"),message.data.get("message"))
            notificationmanager.notify(notificationID, notification)
        }else{
            val pendingIntent=gotoscreen(Constants.Screens.RADIO_SCREEN)
            val notification=picnotification(pendingIntent,message.data.get("title"),message.data.get("message"),message.data.get("image"))
            notificationmanager.notify(notificationID, notification)
        }





    }

    @RequiresApi(Build.VERSION_CODES.O)
    private  fun createNotificationchannnel(notificationManager: NotificationManager){
        val channelName="ChannelName"
        val channel= NotificationChannel(CHANNEL_ID, channelName, IMPORTANCE_DEFAULT).apply {
            description="Repentance and Holiness info"
            enableLights(true)
        }
        notificationManager.createNotificationChannel(channel)
    }

    private fun picnotification(pendingIntent: PendingIntent,title:String?,msg:String?,pic:String?): Notification? {
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val bitmaps= getBitmapfromUrl(pic)
        val myicon = BitmapFactory.decodeResource(resources, R.drawable.dove)

        val notification= NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.dove)
            .setContentTitle(title)
            .setContentText(msg)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)
            .setStyle(
                NotificationCompat.BigPictureStyle()
                    .bigPicture(bitmaps)
                    .bigLargeIcon(myicon)

            )
            .setLargeIcon(bitmaps)
            .build()

        return notification

    }

    private fun wordnotification(pendingIntent: PendingIntent, title:String?, msg:String?): Notification?{
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val myicon = BitmapFactory.decodeResource(resources, R.drawable.dove)
        val notification=NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(msg)
            .setSmallIcon(R.drawable.dove)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(msg)
                    .setBigContentTitle(title)
            )
            .setLargeIcon(myicon)
            .build()

        return  notification
    }
}