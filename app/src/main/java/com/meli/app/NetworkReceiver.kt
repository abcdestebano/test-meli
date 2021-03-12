package com.meli.app

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

abstract class NetworkReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        try {
            val isOnline = context?.isOnline() == true
            broadcastResult(isOnline)
        } catch (e: NullPointerException) {
            e.printStackTrace()
        }
    }

    protected abstract fun broadcastResult(connected: Boolean)
}