package com.example.animetracker.data.network.queries

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.example.animetracker.AnimePageQuery
import com.example.animetracker.SearchAnimeQuery
import com.example.animetracker.data.models.AnimePageModel
import com.example.animetracker.data.models.AnimeSearchModel
import com.example.animetracker.data.network.ApolloConnector

class AnimeQuery(_apolloConnector: ApolloConnector) {

    private val apolloConnector = _apolloConnector

    fun fetchAnime(animeEntry: MutableLiveData<AnimePageModel>,
                   id: Int) {
        val query = AnimePageQuery(Input.optional(id))

        apolloConnector.setupApollo().query(query)
            .enqueue(object : ApolloCall.Callback<AnimePageQuery.Data>() {
                override fun onFailure(e: ApolloException) {
                    Log.d("fail", "No anime found with matching id found")
                }

                override fun onResponse(response: Response<AnimePageQuery.Data>) {
                    val entry = response.data()?.media
                    val anime = AnimePageModel()

                    anime.title = entry?.title?.userPreferred
                    anime.coverImage = entry?.coverImage?.large
                    anime.description = entry?.description
                    anime.status = entry?.status
                    anime.popularity = entry?.popularity
                    anime.meanScore = entry?.meanScore
                    anime.season = entry?.season.toString()
                    anime.seasonYear = entry?.seasonYear
                    anime.episodes = entry?.episodes
                    anime.format = entry?.format

                    animeEntry.postValue(anime)
                }
            })

    }
}