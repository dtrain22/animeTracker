package com.example.animetracker.ui.startup

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import com.example.animetracker.ui.login.LoginActivity

class StartupViewModel : ViewModel() {

    fun determineStartupActivity(context: Context): Intent {
        return Intent(context, LoginActivity::class.java)
    }
}
