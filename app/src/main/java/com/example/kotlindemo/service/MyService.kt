package com.example.kotlindemo.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.example.kotlindemo.activity.MainActivity

/**
 *  前台Service
 */

class MyService : Service(){

    private val mBinder = Binder()
    private val CHANNEL_ID = "foreGround"
    private val CHANNEL_NAME = "FORE_GROUND"

    override fun onCreate() {
        super.onCreate()
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)
            manager.createNotificationChannel(channel)
        }

        val intent = Intent(this, MainActivity::class.java)
        val pi = PendingIntent.getActivity(this, 0, intent, 0)
        val notification = NotificationCompat.Builder(this, CHANNEL_ID).run {
            setContentTitle("This is content title")
            setContentText("This is content text")
            setSmallIcon(0)
            setContentIntent(pi)    //点击通知栏跳转意图
            setAutoCancel(true)     //点击通知栏小图标自动消失
//            setStyle()            //设置长文本，图片等等
            build()
        }
        startForeground(1, notification)
    }

    override fun onBind(intent: Intent?): IBinder? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }


}