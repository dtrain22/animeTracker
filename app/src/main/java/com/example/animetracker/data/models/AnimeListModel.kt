package com.example.animetracker.data.models

import com.example.animetracker.type.MediaListStatus

class AnimeListModel {
    var title: String? = null

    var score: Double? = null

    var progress: Int? = null

    var totalEpisodes: Int? = null

    var status: MediaListStatus? = null

    var coverImage: String? = null

    var mediaId: Int? = null
}