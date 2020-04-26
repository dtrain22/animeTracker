package com.example.animetracker.data.models

import com.example.animetracker.type.MediaFormat
import com.example.animetracker.type.MediaStatus

class AnimePageModel {
    var title: String? = null
    var coverImage: String? = null
    var status: MediaStatus? = null
    var popularity: Int? = null
    var meanScore: Int? = null
    var description: String? = null
    var season: String? = null
    var seasonYear: Int? = null
    var episodes: Int? = null
    var format: MediaFormat? = null
}