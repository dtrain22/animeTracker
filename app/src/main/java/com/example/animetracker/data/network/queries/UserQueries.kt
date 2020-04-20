package com.example.animetracker.data.network.queries

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.apollographql.apollo.request.RequestHeaders
import com.example.animetracker.FetchUserQuery
import com.example.animetracker.data.AccessTokenObject
import com.example.animetracker.data.models.UserModel
import com.example.animetracker.data.network.ApolloConnector

class UserQueries(_apolloConnector: ApolloConnector) {

    private val apolloConnector = _apolloConnector
    private val authHeader = "Bearer " + AccessTokenObject.accessToken

    fun getUserInfo(_userData: MutableLiveData<UserModel>) {
        val userData = UserModel()

        apolloConnector.setupApollo().query(FetchUserQuery())
            .requestHeaders(
                RequestHeaders.builder()
                    .addHeader("Authorization", authHeader)
                    .build()
            )
            .enqueue(object : ApolloCall.Callback<FetchUserQuery.Data>() {
            override fun onFailure(e: ApolloException) {
                Log.d("fail", "No response")
            }

            override fun onResponse(response: Response<FetchUserQuery.Data>) {
                userData.userId = response.data()?.viewer?.id
                userData.name = response.data()?.viewer?.name
                userData.avatar = response.data()?.viewer?.avatar?.medium
                userData.watchCount = response.data()?.viewer?.statistics?.anime?.count
                userData.meanScore = response.data()?.viewer?.statistics?.anime?.meanScore
                userData.minutesWatched = response.data()?.viewer?.statistics?.anime?.minutesWatched
                userData.episodesWatched = response.data()?.viewer?.statistics?.anime?.episodesWatched
                userData.about = response.data()?.viewer?.about
                userData.lastUpdated = response.data()?.viewer?.updatedAt

                _userData.postValue(userData)
            }
        })

    }
}