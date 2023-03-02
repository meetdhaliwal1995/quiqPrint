package com.quiqprint.hub_android

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.util.Log
import android.widget.RemoteViews
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.quiqprint.hub_android.di.NetworkModule
import com.quiqprint.hub_android.di.SharedPreference
import com.quiqprint.hub_android.repo.PrintRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


const val channelId = "notification_channel"
const val channelName = "com.quiqprint.hub_android"


class MyFirebaseMessagingService : FirebaseMessagingService() {

    var printRepository: PrintRepository? = null

    var sharedPreferences: SharedPreferences? = null


    // Generate the notification

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onMessageReceived(message: RemoteMessage) {
        if (message.notification != null) {
            generatNotification(message.notification!!.title!!, message.notification!!.body!!)

//            printRepository = PrintRepository(NetworkModule.myApi())
//            val sharedPreferences = SharedPreference(this)
//            val printId = message.data[message.notification!!.body]

//            Log.e("printIDdddd", printId.toString())
//            CoroutineScope(Dispatchers.IO).launch {
//                printRepository?.getCommandData(printId.toString())
//                    ?.collectLatest {
//                        Log.e("apiResponseeee", it.toString())
//                        printRepository!!.getSingleCommandData(it!![0]?.singles?.get(0) ?: "")
//                            .collectLatest { _singleData ->
//                                Log.e("apiResponseeeeSingle", _singleData.toString())
//                                sharedPreferences.singleImageData = _singleData[0].path
//                            }
//                    }
//            }

        }
    }

    @SuppressLint("RemoteViewLayout")
    fun getRemoteView(title: String, message: String): RemoteViews {
        val remoteView = RemoteViews(channelName, R.layout.fcm_notifications)

        remoteView.setTextViewText(R.id.noti_title, title)
        remoteView.setTextViewText(R.id.noti_body, message)
        remoteView.setImageViewResource(
            R.id.ivNoti,
            com.google.android.gms.base.R.drawable.common_google_signin_btn_icon_dark
        )

        return remoteView
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun generatNotification(title: String, message: String) {

        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_MUTABLE)

        var builder: NotificationCompat.Builder =
            NotificationCompat.Builder(applicationContext, channelId)
                .setSmallIcon(com.google.firebase.messaging.R.drawable.common_google_signin_btn_icon_dark)
                .setAutoCancel(true)
                .setVibrate(longArrayOf(1000, 1000, 1000, 1000))
                .setOnlyAlertOnce(true)
                .setContentIntent(pendingIntent)

        builder = builder.setContent(getRemoteView(title, message))

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel =
                NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(notificationChannel)
        }

        notificationManager.notify(0, builder.build())

    }
}