package com.tarikmedjber.spacexclient.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tarikmedjber.spacexclient.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, HomeFragment.newInstance())
                .commitNow()
        }
    }
}