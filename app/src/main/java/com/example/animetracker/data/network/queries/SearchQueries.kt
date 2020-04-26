package com.example.animetracker.data.network.queries

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.example.animetracker.SearchAnimeQuery
import com.example.animetracker.data.models.AnimeSearchModel
import com.example.animetracker.data.network.ApolloConnector

class SearchQueries(_apolloConnector: ApolloConnector) {

    private val apolloConnector = _apolloConnector

    fun searchAnime(search: String,
                    searchResult: MutableLiveData<MutableList<AnimeSearchModel>>) {
        val searchQuery = SearchAnimeQuery(Input.optional(search))

        apolloConnector.setupApollo().query(searchQuery)
            .enqueue(object : ApolloCall.Callback<SearchAnimeQuery.Data>() {
                override fun onFailure(e: ApolloException) {
                    Log.d("fail", "No search response")
                }

                override fun onResponse(response: Response<SearchAnimeQuery.Data>) {
                    val lists = response.data()?.page?.media
                    val animeList = mutableListOf<AnimeSearchModel>()

                    if (lists != null) {
                        for (item in lists) {
                            val result = AnimeSearchModel()
                            result.id = item?.id
                            result.title = item?.title?.userPreferred
                            result.coverImage = item?.coverImage?.medium
                            result.meanScore = (item?.meanScore?.toFloat()?.div(10))
                            result.season = item?.season.toString()
                            result.seasonYear = item?.seasonYear

                            animeList.add(result)
                        }
                    }

                    searchResult.postValue(animeList)
                }
            })

    }
}