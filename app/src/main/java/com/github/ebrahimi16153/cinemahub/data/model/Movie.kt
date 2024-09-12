package com.github.ebrahimi16153.cinemahub.data.model

import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("adult")
    val adult: Boolean?, // false
    @SerializedName("backdrop_path")
    val backdropPath: String?, // /xg27NrXi7VXCGUr7MG75UqLl6Vg.jpg
    @SerializedName("genre_ids")
    val genreIds: List<Int?>?,
    @SerializedName("id")
    val id: Int, // 1022789
    @SerializedName("original_language")
    val originalLanguage: String?, // en
    @SerializedName("original_title")
    val originalTitle: String?, // Inside Out 2
    @SerializedName("overview")
    val overview: String?, // Teenager Riley's mind headquarters is undergoing a sudden demolition to make room for something entirely unexpected: new Emotions! Joy, Sadness, Anger, Fear and Disgust, who’ve long been running a successful operation by all accounts, aren’t sure how to feel when Anxiety shows up. And it looks like she’s not alone.
    @SerializedName("popularity")
    val popularity: Double?, // 10032.488
    @SerializedName("poster_path")
    val posterPath: String?, // /vpnVM9B6NMmQpWeZvzLvDESb2QY.jpg
    @SerializedName("release_date")
    val releaseDate: String?, // 2024-06-11
    @SerializedName("title")
    val title: String?, // Inside Out 2
    @SerializedName("video")
    val video: Boolean?, // false
    @SerializedName("vote_average")
    val voteAverage: Double?, // 7.714
    @SerializedName("vote_count")
    val voteCount: Int? // 1541
)