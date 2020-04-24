package com.example.animetracker.ui.profile.user_anime_list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.animetracker.data.models.AnimeListModel
import com.example.animetracker.data.network.ApolloConnector
import com.example.animetracker.data.network.queries.UserListQueries
import okhttp3.OkHttpClient

class AnimeListViewModel : ViewModel() {
    private val okayHttpClient = OkHttpClient.Builder().build()
    private val apolloConnector = ApolloConnector(okayHttpClient)

    val userList: MutableLiveData<MutableList<AnimeListModel>> by lazy {
        MutableLiveData<MutableList<AnimeListModel>>()
    }

    fun getUserList() {
        UserListQueries(apolloConnector).getUserList(userList)
    }
}
