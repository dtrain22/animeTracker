package com.example.animetracker.data.network.queries

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.example.animetracker.FetchUserListQuery
import com.example.animetracker.data.AccessTokenObject
import com.example.animetracker.data.models.AnimeListModel
import com.example.animetracker.data.network.ApolloConnector

class UserListQueries(_apolloConnector: ApolloConnector) {

    private val apolloConnector = _apolloConnector
    private val authHeader = "Bearer " + AccessTokenObject.accessToken

    fun getUserList(userList: MutableLiveData<MutableList<AnimeListModel>>) {
        apolloConnector.setupApollo().query(FetchUserListQuery())
            .enqueue(object : ApolloCall.Callback<FetchUserListQuery.Data>() {
                override fun onFailure(e: ApolloException) {
                    Log.d("fail", "No response")
                }

                override fun onResponse(response: Response<FetchUserListQuery.Data>) {
                    val lists = response.data()?.mediaListCollection?.lists
                    val animeList = mutableListOf<AnimeListModel>()

                    if(lists?.get(0)?.entries != null){
                        for (list in lists)
                            for (item in list?.entries!!) {
                                val animeListModel = AnimeListModel()

                                animeListModel.progress = item?.progress
                                animeListModel.score = item?.score
                                animeListModel.status = item?.status.toString()
                                animeListModel.totalEpisodes = item?.media?.episodes
                                animeListModel.title = item?.media?.title?.english
                                animeListModel.coverImage = item?.media?.coverImage?.medium

                                animeList.add(animeListModel)
                            }
                    }

                    userList.postValue(animeList)

                    Log.d("response", userList.value.toString())
                }
            })

    }
}