package com.example.animetracker.ui.anime.anime_search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.animetracker.data.models.AnimeSearchModel
import com.example.animetracker.data.network.ApolloConnector
import com.example.animetracker.data.network.queries.SearchQueries
import okhttp3.OkHttpClient

class AnimeSearchViewModel : ViewModel() {
    private val okayHttpClient = OkHttpClient.Builder().build()
    private val apolloConnector = ApolloConnector(okayHttpClient)

    val animeSearch: MutableLiveData<MutableList<AnimeSearchModel>> by lazy {
        MutableLiveData<MutableList<AnimeSearchModel>>()
    }

    fun searchAnime(search: String){
        SearchQueries(apolloConnector).searchAnime(search, animeSearch)
    }
}
