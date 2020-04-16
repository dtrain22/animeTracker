package com.example.animetracker.ui.login

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.preference.PreferenceManager
import com.example.animetracker.BuildConfig
import com.example.animetracker.data.AuthCodeHandler
import com.example.animetracker.data.network.repositories.AccessTokenRepository
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import java.util.concurrent.TimeUnit

class LoginViewModel(
    _accessTokenRepository: AccessTokenRepository = AccessTokenRepository()
) : ViewModel() {

    private val accessTokenRepository = _accessTokenRepository

    val accessToken: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    fun handleAuthenticationResponse(uri: Uri?, context: Context){
        val authCodeHandler = AuthCodeHandler(context)
        if (uri != null && uri.toString().startsWith(BuildConfig.REDIRECT_URI)) {
            // use the parameter your API exposes for the code (mostly it's "code")
            val code = uri.getQueryParameter("code")
            if (code != null) {
                authCodeHandler.storeAuthCode(code)
                accessTokenRepository.getAccessToken(code, accessToken)
            } else if (uri.getQueryParameter("error") != null) {
                Log.e("Error","error retrieving authorization code")
            }
        }
    }
}
