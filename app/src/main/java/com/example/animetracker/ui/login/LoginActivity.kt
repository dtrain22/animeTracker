package com.example.animetracker.ui.login

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.animetracker.BuildConfig
import com.example.animetracker.R
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import java.io.IOException
import java.util.concurrent.TimeUnit

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

        val uri = intent.data
        if (uri != null && uri.toString().startsWith(BuildConfig.REDIRECT_URI)) {
            // use the parameter your API exposes for the code (mostly it's "code")
            val code = uri.getQueryParameter("code")
            if (code != null) {
                Log.d("Success","success retrieving authorization code")
                getAuthenticationToken(code)
                //Log.d("Success",accessToken.toString())
            } else if (uri.getQueryParameter("error") != null) {
                Log.e("Error","error retrieving authorization code")
            }
        }
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

    private fun getAuthenticationToken(code: String)  {

        try {
            val client = OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10,TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build()

            val formBody = FormBody.Builder()
                .add("grant_type", "authorization_code")
                .add("client_id", BuildConfig.CLIENT_ID)
                .add("client_secret", BuildConfig.CLIENT_SECRET)
                .add("redirect_uri", BuildConfig.REDIRECT_URI)
                .add("code",code)
                .build()

            val request = Request.Builder()
                .url("https://anilist.co/api/v2/oauth/token")
                .post(formBody)
                .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.e("Error","failure")
                }

                override fun onResponse(call: Call, response: Response) {
                    //val accessToken  = Uri.parse(response.)
                    response.use {
                        Log.d("Success",response.body()!!.string())
                    }
                }
            })
        }  catch (e: IOException) {
            e.printStackTrace()
        }
    }



}