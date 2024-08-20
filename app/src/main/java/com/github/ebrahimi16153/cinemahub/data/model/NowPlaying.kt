package com.github.ebrahimi16153.cinemahub.data.model

import com.google.gson.annotations.SerializedName

data class NowPlayingMovie(
    @SerializedName("dates")
    val dates: Dates?,
    @SerializedName("page")
    val page: Int?, // 1
    @SerializedName("results")
    val results: List<Movie>,
    @SerializedName("total_pages")
    val totalPages: Int?, // 201
    @SerializedName("total_results")
    val totalResults: Int? // 4012
) {
    data class Dates(
        @SerializedName("maximum")
        val maximum: String?, // 2024-07-17
        @SerializedName("minimum")
        val minimum: String? // 2024-06-05
    )

}
