package com.github.ebrahimi16153.cinemahub.data.model


import com.google.gson.annotations.SerializedName

data class MovieTrailers(
    @SerializedName("id")
    val id: Int?, // 533535
    @SerializedName("results")
    val results: List<Result?>?
) {
    data class Result(
        @SerializedName("id")
        val id: String?, // 66cf58ff5ba7b02c0459a72e
        @SerializedName("iso_3166_1")
        val iso31661: String?, // US
        @SerializedName("iso_639_1")
        val iso6391: String?, // en
        @SerializedName("key")
        val key: String?, // nY5IAhjTclY
        @SerializedName("name")
        val name: String?, // The Visual Effects of Cassandra Nova's Powers
        @SerializedName("official")
        val official: Boolean?, // true
        @SerializedName("published_at")
        val publishedAt: String?, // 2024-08-28T17:00:47.000Z
        @SerializedName("site")
        val site: String?, // YouTube
        @SerializedName("size")
        val size: Int?, // 1080
        @SerializedName("type")
        val type: String? // Behind the Scenes
    )
}