package com.example.animetracker.data

import android.content.Context


class AuthCodeHandler(_context: Context) {

    val context = _context

    private val prefs = context.getSharedPreferences("com.example.app", Context.MODE_PRIVATE)
    private val editor = prefs.edit()

    fun storeAuthCode(authCode: String){
        editor.putString("auth_code",authCode)
        editor.apply()
    }

    fun retrieveAuthCode():String?{
        return prefs.getString("auth_code",null)
    }
}