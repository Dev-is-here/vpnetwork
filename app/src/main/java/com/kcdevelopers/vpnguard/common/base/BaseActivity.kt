package com.kcdevelopers.vpnguard.common.base

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.kcdevelopers.core_module.connectivity.base.ConnectivityProvider
import com.kcdevelopers.core_module.connectivity.base.hasInternet

abstract class BaseActivity : AppCompatActivity(), ConnectivityProvider.ConnectivityStateListener {
    private val provider: ConnectivityProvider by lazy { ConnectivityProvider.createProvider(this) }

    val hasInternet: Boolean get() {
        return provider.getNetworkState().hasInternet()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            //ToDo
        }
    }
}