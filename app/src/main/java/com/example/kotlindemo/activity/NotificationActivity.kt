package com.example.kotlindemo.activity

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat

/**
 * 通知管理
 *  Android8.0之后增加通知渠道（每一条通知对应一个通知渠道，方便用户管理）
 */
class NotificationActivity : BaseActivity() {

    private val CHANNEL_ID = "normal"
    private val CHANNEL_NAME = "Normal"

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)
            manager.createNotificationChannel(channel)
        }

        val intent = Intent(this, FirstActivity::class.java)
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
        manager.notify(1, notification)

    }

}