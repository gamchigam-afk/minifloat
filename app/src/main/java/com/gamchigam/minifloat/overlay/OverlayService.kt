package com.gamchigam.minifloat.overlay

import android.app.Service
import android.content.Intent
import android.os.IBinder

class OverlayService : Service() {

    private lateinit var overlayManager: OverlayManager

    override fun onCreate() {
        super.onCreate()

        overlayManager = OverlayManager(this)
        overlayManager.showFloatingButton()
    }

    override fun onDestroy() {
        overlayManager.removeOverlay()
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}
