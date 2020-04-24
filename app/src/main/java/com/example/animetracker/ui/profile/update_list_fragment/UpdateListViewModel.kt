package com.example.animetracker.ui.profile.update_list_fragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.animetracker.data.models.AnimeListModel
import com.example.animetracker.data.network.ApolloConnector
import com.example.animetracker.data.network.mutations.UserListMutations
import com.example.animetracker.type.MediaListStatus
import okhttp3.OkHttpClient

class UpdateListViewModel : ViewModel() {
    private val okayHttpClient = OkHttpClient.Builder().build()
    private val apolloConnector = ApolloConnector(okayHttpClient)

    val listUpdated: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    fun updateUserList(entry: AnimeListModel, score: Int){
        val mediaID = entry.mediaId!!
        val status = entry.status!!
        val totalEpisodes = entry.totalEpisodes!!

        UserListMutations(apolloConnector).updateUserEntry(mediaID,
            status,
            (score * 10),
            totalEpisodes,
            listUpdated)
    }
}
