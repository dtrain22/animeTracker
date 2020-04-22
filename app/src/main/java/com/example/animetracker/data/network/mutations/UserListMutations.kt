package com.example.animetracker.data.network.mutations

import android.util.Log
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.apollographql.apollo.request.RequestHeaders
import com.example.animetracker.UpdateUserListMutation
import com.example.animetracker.data.AccessTokenObject
import com.example.animetracker.data.network.ApolloConnector
import com.example.animetracker.type.MediaListStatus

class UserListMutations(_apolloConnector: ApolloConnector) {

    private val apolloConnector = _apolloConnector
    private val authHeader = "Bearer " + AccessTokenObject.accessToken

    fun updateUserEntry(_mediaID: Int,
                        _status: MediaListStatus,
                        _rawScore: Int,
                        _progress: Int) {

        val updateUserList = UpdateUserListMutation(Input.optional(_mediaID),
            Input.optional(_status),
            Input.optional(_rawScore),
            Input.optional(_progress))


        apolloConnector.setupApollo().mutate(updateUserList)
            .requestHeaders(
                RequestHeaders.builder()
                    .addHeader("Authorization", authHeader)
                    .build()
            )
            .enqueue(object : ApolloCall.Callback<UpdateUserListMutation.Data>() {
                override fun onFailure(e: ApolloException) {
                    Log.d("failed", e.toString())
                }

                override fun onResponse(response: Response<UpdateUserListMutation.Data>) {
                    Log.d("Success", "Info successfully updated")
                }
            })

    }
}