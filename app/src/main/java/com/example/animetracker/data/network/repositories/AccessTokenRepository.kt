package com.example.animetracker.data.network.repositories

import android.util.Log
import com.example.animetracker.BuildConfig
import com.example.animetracker.data.AccessTokenObject
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import java.util.concurrent.TimeUnit

class AccessTokenRepository(
    val accessTokenObject:AccessTokenObject = AccessTokenObject
) {

    fun getAccessToken(code:String) {
        try {
            val client = OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
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
                    val jsonData: String = response.body()!!.string()
                    val jsonObject = JSONObject(jsonData)

                    accessTokenObject.accessToken = jsonObject.getString("access_token")
                }
            })
        }  catch (e: IOException) {
            e.printStackTrace()
        }
    }
}