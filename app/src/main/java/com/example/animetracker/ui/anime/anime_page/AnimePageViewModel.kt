package com.example.animetracker.ui.anime.anime_page

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.animetracker.data.models.AnimePageModel
import com.example.animetracker.data.models.UserModel
import com.example.animetracker.data.network.ApolloConnector
import com.example.animetracker.data.network.queries.AnimeQuery
import okhttp3.OkHttpClient

class AnimePageViewModel : ViewModel() {
    private val okayHttpClient = OkHttpClient.Builder().build()
    private val apolloConnector = ApolloConnector(okayHttpClient)

    val animeEntry: MutableLiveData<AnimePageModel> by lazy {
        MutableLiveData<AnimePageModel>()
    }

    fun fetchAnimeInfo(id: Int) {
        AnimeQuery(apolloConnector).fetchAnime(animeEntry, id)
    }
}
