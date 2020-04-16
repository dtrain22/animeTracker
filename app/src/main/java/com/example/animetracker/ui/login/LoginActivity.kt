package com.example.animetracker.ui.login

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.animetracker.BuildConfig
import com.example.animetracker.R
import kotlinx.android.synthetic.main.activity_main.*


class LoginActivity : AppCompatActivity() {

    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_fragment)
        setSupportActionBar(toolbar)

        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
    }

    override fun onResume() {
        super.onResume()

        viewModel.handleAuthenticationResponse(intent.data,this)
    }

    fun onLoginClick(view: View) {

        val intent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("https://anilist.co/api/v2/oauth/authorize" + "?client_id=" + BuildConfig.CLIENT_ID + "&redirect_uri=" + BuildConfig.REDIRECT_URI + "&response_type=code")
        )
        startActivity(intent)

        //val intent = Intent(this@LoginActivity, MainActivity::class.java)
        //startActivity(intent)
        //finish()
    }
}