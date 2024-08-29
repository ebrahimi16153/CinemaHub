package com.github.ebrahimi16153.cinemahub.data.model

import com.google.gson.annotations.SerializedName

data class ImageCollection(
    @SerializedName("backdrops")
    val backdrops: List<Backdrop?>?,
    @SerializedName("id")
    val id: Int?, // 85861
    @SerializedName("posters")
    val posters: List<Poster>
) {
    data class Backdrop(
        @SerializedName("aspect_ratio")
        val aspectRatio: Double?, // 1.778
        @SerializedName("file_path")
        val filePath: String?, // /k0DEUZqbB9bVcm2NsVaMkdd4Vs8.jpg
        @SerializedName("height")
        val height: Int?, // 1080
        @SerializedName("iso_639_1")
        val iso6391: String?, // en
        @SerializedName("vote_average")
        val voteAverage: Double?, // 5.318
        @SerializedName("vote_count")
        val voteCount: Int?, // 3
        @SerializedName("width")
        val width: Int? // 1920
    )

    data class Poster(
        @SerializedName("aspect_ratio")
        val aspectRatio: Double?, // 0.667
        @SerializedName("file_path")
        val filePath: String?, // /6GZA6Mla1lKccVVUuhLU1LQoZAe.jpg
        @SerializedName("height")
        val height: Int?, // 1500
        @SerializedName("iso_639_1")
        val iso6391: String?, // pt
        @SerializedName("vote_average")
        val voteAverage: Double?, // 5.384
        @SerializedName("vote_count")
        val voteCount: Int?, // 2
        @SerializedName("width")
        val width: Int? // 1000
    )
}