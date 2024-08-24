package com.github.ebrahimi16153.cinemahub.data.model

import com.google.gson.annotations.SerializedName

data class GenresOfMovie(
    @SerializedName("genres")
    val genres: List<Genre>
)