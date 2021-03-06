package com.example.animetracker.data.network.queries

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.example.animetracker.FetchUserListQuery
import com.example.animetracker.data.models.AnimeListModel
import com.example.animetracker.data.network.ApolloConnector

class UserListQueries(_apolloConnector: ApolloConnector) {

    private val apolloConnector = _apolloConnector

    fun getUserList(userList: MutableLiveData<MutableList<AnimeListModel>>, userID: Int?) {

        val query = FetchUserListQuery(Input.optional(userID))

        apolloConnector.setupApollo().query(query)
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
                                animeListModel.status = item?.status
                                animeListModel.totalEpisodes = item?.media?.episodes
                                animeListModel.title = item?.media?.title?.userPreferred
                                animeListModel.coverImage = item?.media?.coverImage?.medium
                                animeListModel.mediaId = item?.mediaId

                                animeList.add(animeListModel)
                            }
                    }

                    animeList.sortBy { it.title }

                    userList.postValue(animeList)
                }
            })

    }
}