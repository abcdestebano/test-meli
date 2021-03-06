package com.meli.app.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.meli.app.utils.extensions.isOnline

/**
 * This abstract class will be listening the state of Network
 * just to give the correct feedback to the user
 */
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