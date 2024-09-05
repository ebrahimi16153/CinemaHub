package com.github.ebrahimi16153.cinemahub.data.model


import com.google.gson.annotations.SerializedName

data class MovieImages(
    @SerializedName("backdrops")
    val backdrops: List<Backdrop?>?,
    @SerializedName("id")
    val id: Int?, // 917496
    @SerializedName("logos")
    val logos: List<Logo?>?,
    @SerializedName("posters")
    val posters: List<Poster>
) {
    data class Backdrop(
        @SerializedName("aspect_ratio")
        val aspectRatio: Double?, // 1.778
        @SerializedName("file_path")
        val filePath: String?, // /r14GYnHxLT2EAP38JMPu8aRIGrr.jpg
        @SerializedName("height")
        val height: Int?, // 2160
        @SerializedName("iso_639_1")
        val iso6391: String?, // en
        @SerializedName("vote_average")
        val voteAverage: Double?, // 5.312
        @SerializedName("vote_count")
        val voteCount: Int?, // 1
        @SerializedName("width")
        val width: Int? // 3840
    )

    data class Logo(
        @SerializedName("aspect_ratio")
        val aspectRatio: Double?, // 1.775
        @SerializedName("file_path")
        val filePath: String?, // /lm29dqolGGSaPMWaVVKPvJ6gMGF.png
        @SerializedName("height")
        val height: Int?, // 1299
        @SerializedName("iso_639_1")
        val iso6391: String?, // en
        @SerializedName("vote_average")
        val voteAverage: Double?, // 5.312
        @SerializedName("vote_count")
        val voteCount: Int?, // 1
        @SerializedName("width")
        val width: Int? // 2306
    )

    data class Poster(
        @SerializedName("aspect_ratio")
        val aspectRatio: Double?, // 0.667
        @SerializedName("file_path")
        val filePath: String?, // /kKgQzkUCnQmeTPkyIwHly2t6ZFI.jpg
        @SerializedName("height")
        val height: Int?, // 3000
        @SerializedName("iso_639_1")
        val iso6391: String?, // en
        @SerializedName("vote_average")
        val voteAverage: Double?, // 5.582
        @SerializedName("vote_count")
        val voteCount: Int?, // 9
        @SerializedName("width")
        val width: Int? // 2000
    )
}