package com.meli.app

import android.content.Context
import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.meli.app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var networkReceiver = object : NetworkReceiver() {
        override fun broadcastResult(connected: Boolean) {
            if (!connected) {
                setEmptyStateOffline()
            }
        }
    }

    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setEmptyState()
        handleClickListeners()
        registerReceiverNetworkState()
    }

    private fun setEmptyState() {
        binding?.emptyState?.apply {
            setContentView(
                image = R.drawable.ic_search_meli,
                text = resources.getString(R.string.text_search_empty_state),
            )
        }
    }

    private fun setEmptyStateOffline() {
        binding?.emptyState?.apply {
            setContentView(
                image = R.drawable.ic_satellite,
                text = resources.getString(R.string.text_offline_empty_state),
                showButton = true
            ) { if (context.isOnline()) setEmptyState() }
        }
    }

    private fun handleClickListeners() {
        binding?.apply {
            imgMenu.setOnClickListener { showComingSoon() }
            imgShoppingCart.setOnClickListener { showComingSoon() }
        }
    }

    private fun registerReceiverNetworkState() {
        registerReceiver(
            networkReceiver,
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        )
    }

    private fun showComingSoon() {
        Toast.makeText(
            applicationContext,
            resources.getString(R.string.text_coming_soon),
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(networkReceiver)
        binding = null
    }
}