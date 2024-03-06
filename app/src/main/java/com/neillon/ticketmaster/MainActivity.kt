package com.neillon.ticketmaster

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.neillon.common.delegates.viewBinding
import com.neillon.common.exts.commit
import com.neillon.common.network.ConnectivityChecker
import com.neillon.ticketmaster.databinding.ActivityMainBinding
import com.neillon.events.ui.EventsFragment
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by viewBinding(ActivityMainBinding::inflate)
    private val connectivityChecker by inject<ConnectivityChecker>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        connectivityChecker.register(applicationContext)

        supportFragmentManager.commit {
            replace(R.id.fragment_container, EventsFragment.newInstance())
        }
    }
}
