package com.gamchigam.minifloat.overlay

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.gamchigam.minifloat.R

class OverlayService : Service() {

    private lateinit var overlayManager: OverlayManager

    companion object {
        private const val CHANNEL_ID = "minifloat_overlay"
        private const val NOTIFICATION_ID = 1001
    }

    override fun onCreate() {
        super.onCreate()

        createNotificationChannel()

        startForeground(
            NOTIFICATION_ID,
            createNotification()
        )

        overlayManager = OverlayManager(this)
        overlayManager.showFloatingButton()
    }

    override fun onDestroy() {
        if (::overlayManager.isInitialized) {
            overlayManager.removeOverlay()
        }

        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "MiniFloat",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "MiniFloat 플로팅 버튼 서비스"
                setShowBadge(false)
            }

            val manager =
                getSystemService(NotificationManager::class.java)

            manager.createNotificationChannel(channel)
        }
    }

    private fun createNotification(): Notification {
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("MiniFloat 실행 중")
            .setContentText("플로팅 버튼을 사용할 수 있습니다.")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setOngoing(true)
            .setCategory(NotificationCompat.CATEGORY_SERVICE)
            .build()
    }
}
