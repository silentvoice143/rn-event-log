package com.rneventlog.core.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.provider.Settings
import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter

import com.rneventlog.core.debug.DebugEmitter
import com.rneventlog.core.flush.FlushManager


object NetworkManager {

  private var context:
    Context? = null

  private var connected =
    false

  private val airplaneModeReceiver =

    object : BroadcastReceiver() {

      override fun onReceive(
        context: Context?,
        intent: Intent?
      ) {

        if (
          isAirplaneModeEnabled()
        ) {

          DebugEmitter.emit(
            "Airplane Mode Enabled"
          )

        } else {

          DebugEmitter.emit(
            "Airplane Mode Disabled"
          )
        }
      }
    }

  fun initialize(
    appContext: Context
  ) {

    context =
      appContext.applicationContext

    checkAirplaneMode()

    registerAirplaneModeReceiver()

    registerNetworkCallback()
  }

  fun isConnected():
    Boolean {

    return connected
  }

  fun getNetworkType(): NetworkType {

    val ctx =
      context ?: return NetworkType.UNKNOWN

    val connectivityManager =

      ctx.getSystemService(
        Context.CONNECTIVITY_SERVICE
      ) as ConnectivityManager

    val network =
      connectivityManager.activeNetwork
        ?: return NetworkType.UNKNOWN

    val capabilities =

      connectivityManager
        .getNetworkCapabilities(
          network
        )
        ?: return NetworkType.UNKNOWN

    return when {

      capabilities.hasTransport(
        NetworkCapabilities.TRANSPORT_WIFI
      ) -> NetworkType.WIFI

      capabilities.hasTransport(
        NetworkCapabilities.TRANSPORT_CELLULAR
      ) -> NetworkType.CELLULAR

      capabilities.hasTransport(
        NetworkCapabilities.TRANSPORT_ETHERNET
      ) -> NetworkType.ETHERNET

      else -> NetworkType.UNKNOWN
    }
  }

  private fun registerAirplaneModeReceiver() {

    val filter =
      IntentFilter(
        Intent.ACTION_AIRPLANE_MODE_CHANGED
      )

    context?.registerReceiver(

      airplaneModeReceiver,

      filter
    )
  }

  private fun registerNetworkCallback() {

    val ctx =
      context ?: return

    val connectivityManager =

      ctx.getSystemService(
        Context.CONNECTIVITY_SERVICE
      ) as ConnectivityManager

    connected =
      hasInternet(
        connectivityManager
      )

    connectivityManager
      .registerDefaultNetworkCallback(

        object :
          ConnectivityManager.NetworkCallback() {

          override fun onAvailable(
            network: Network
          ) {

            connected = true

            DebugEmitter.emit(
              "Network Connected"
            )

            FlushManager.flush()
          }

          override fun onLost(
            network: Network
          ) {

            connected = false

            DebugEmitter.emit(
              "Network Disconnected"
            )
          }
        }
      )
  }

  fun isAirplaneModeEnabled():
    Boolean {

    val ctx =
      context ?: return false

    return Settings.Global.getInt(

      ctx.contentResolver,

      Settings.Global
        .AIRPLANE_MODE_ON,

      0

    ) != 0
  }

  fun isMetered():
    Boolean {

    val ctx =
      context ?: return false

    val connectivityManager =

      ctx.getSystemService(
        Context.CONNECTIVITY_SERVICE
      ) as ConnectivityManager

    return connectivityManager
      .isActiveNetworkMetered
  }

  private fun checkAirplaneMode() {

    if (
      isAirplaneModeEnabled()
    ) {

      DebugEmitter.emit(
        "Airplane Mode Enabled"
      )

    } else {

      DebugEmitter.emit(
        "Airplane Mode Disabled"
      )
    }
  }

  private fun hasInternet(
    connectivityManager:
    ConnectivityManager
  ): Boolean {

    val network =
      connectivityManager
        .activeNetwork

        ?: return false

    val capabilities =

      connectivityManager
        .getNetworkCapabilities(
          network
        )

        ?: return false

    return capabilities.hasCapability(
      NetworkCapabilities.NET_CAPABILITY_INTERNET
    )
  }
}
