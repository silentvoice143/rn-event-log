package com.rneventlog.core.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

object NetworkManager {

  private var context:
    Context? = null

  fun initialize(
    appContext: Context
  ) {

    context =
      appContext.applicationContext
  }

  fun isConnected():
    Boolean {

    return try {

      val ctx =
        context ?: return false

      val connectivityManager =

        ctx.getSystemService(
          Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager

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

      capabilities.hasCapability(
        NetworkCapabilities.NET_CAPABILITY_INTERNET
      )

    } catch (e: Exception) {

      false
    }
  }
}