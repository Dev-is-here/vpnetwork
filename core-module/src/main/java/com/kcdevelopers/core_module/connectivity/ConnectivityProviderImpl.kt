package com.kcdevelopers.core_module.connectivity

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresApi
import com.kcdevelopers.core_module.connectivity.base.ConnectivityProvider
import com.kcdevelopers.core_module.connectivity.base.ConnectivityProviderBaseImpl

@RequiresApi(Build.VERSION_CODES.N)
class ConnectivityProviderImpl(private val cm: ConnectivityManager) : ConnectivityProviderBaseImpl() {
    private val networkCallback = ConnectivityCallback()

    override fun subscribe() {
        cm.registerDefaultNetworkCallback(networkCallback)
    }

    override fun unsubscribe() {
        cm.unregisterNetworkCallback(networkCallback)
    }

    override fun getNetworkState(): ConnectivityProvider.NetworkState {
        val capabilities = cm.getNetworkCapabilities(cm.activeNetwork)
        return if (capabilities != null) {
            ConnectivityProvider.NetworkState.ConnectedState.Connected(capabilities)
        } else {
            ConnectivityProvider.NetworkState.NotConnectedState
        }
    }

    private inner class ConnectivityCallback : ConnectivityManager.NetworkCallback() {
        override fun onCapabilitiesChanged(
            network: Network,
            networkCapabilities: NetworkCapabilities
        ) {
            dispatchChange(ConnectivityProvider.NetworkState.ConnectedState.Connected(networkCapabilities))
        }

        override fun onLost(network: Network) {
            dispatchChange(ConnectivityProvider.NetworkState.NotConnectedState)
        }
    }
}