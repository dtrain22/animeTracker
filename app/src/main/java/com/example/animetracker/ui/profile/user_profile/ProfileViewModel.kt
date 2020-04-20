package com.example.animetracker.ui.profile.user_profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.animetracker.data.models.AnimeListModel
import com.example.animetracker.data.models.UserModel
import com.example.animetracker.data.network.ApolloConnector
import com.example.animetracker.data.network.queries.UserListQueries
import com.example.animetracker.data.network.queries.UserQueries
import okhttp3.OkHttpClient

class ProfileViewModel : ViewModel() {
    private val okayHttpClient = OkHttpClient.Builder().build()
    private val apolloConnector = ApolloConnector(okayHttpClient)

    val userData: MutableLiveData<UserModel> by lazy {
        MutableLiveData<UserModel>()
    }

    fun updateUserData() {
        UserQueries(apolloConnector).getUserInfo(userData)
    }

}
