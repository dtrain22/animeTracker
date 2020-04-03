package com.example.animetracker.data.network.queries

import androidx.lifecycle.MutableLiveData
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.example.animetracker.UserQuery
import com.example.animetracker.data.models.UserModel
import com.example.animetracker.data.network.ApolloConnector

class UserQueries(_apolloConnector: ApolloConnector) {

    private val apolloConnector = _apolloConnector

    fun getUserInfo(_userData: MutableLiveData<UserModel>) {
        val userData = UserModel()

        apolloConnector.setupApollo().query(
            UserQuery
                .builder()
                .name("dtrain22")
                .build()
        ).enqueue(object : ApolloCall.Callback<UserQuery.Data>() {
            override fun onFailure(e: ApolloException) {

            }

            override fun onResponse(response: Response<UserQuery.Data>) {
                userData.userId = response.data()?.User()?.id()
                userData.name = response.data()?.User()?.name()
                userData.avatar = response.data()?.User()?.avatar()?.medium()
                userData.watchCount = response.data()?.User()?.statistics()?.anime()?.count()
                userData.meanScore = response.data()?.User()?.statistics()?.anime()?.meanScore()
                userData.minutesWatched = response.data()?.User()?.statistics()?.anime()?.minutesWatched()
                userData.episodesWatched = response.data()?.User()?.statistics()?.anime()?.episodesWatched()
                userData.about = response.data()?.User()?.about()
                userData.lastUpdated = response.data()?.User()?.updatedAt()

                _userData.postValue(userData)
            }
        })

    }
}