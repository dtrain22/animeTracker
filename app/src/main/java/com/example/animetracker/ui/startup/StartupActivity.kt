package com.example.animetracker.ui.startup

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

import com.example.animetracker.R

class StartupActivity : AppCompatActivity() {

    private lateinit var viewModel: StartupViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.startup_fragment)

        viewModel = ViewModelProvider(this).get(StartupViewModel::class.java)

        val intent = viewModel.determineStartupActivity(this)
        startActivity(intent)
        finish()
    }

}
