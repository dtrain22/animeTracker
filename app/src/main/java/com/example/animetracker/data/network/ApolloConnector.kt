package com.example.animetracker.data.network

import com.apollographql.apollo.ApolloClient
import okhttp3.OkHttpClient


const val BASE_URL = "https://graphql.anilist.co"

class ApolloConnector(
    _okHttpClient: OkHttpClient
) {

    val okHttpClient = _okHttpClient

    fun setupApollo() : ApolloClient {
        return ApolloClient.builder()
            .serverUrl(BASE_URL)
            .okHttpClient(okHttpClient)
            .build()
    }

}