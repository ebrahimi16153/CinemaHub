package com.github.ebrahimi16153.cinemahub.data.model

import com.google.gson.annotations.SerializedName

data class Credits(
    @SerializedName("cast")
    val cast: List<Cast>,
    @SerializedName("crew")
    val crew: List<Crew>,
    @SerializedName("id")
    val id: Int? // 748783
) {
    data class Cast(
        @SerializedName("adult")
        val adult: Boolean?, // false
        @SerializedName("cast_id")
        val castId: Int?, // 21
        @SerializedName("character")
        val character: String?, // Garfield (voice)
        @SerializedName("credit_id")
        val creditId: String?, // 61805556c7c2240043b16390
        @SerializedName("gender")
        val gender: Int?, // 2
        @SerializedName("id")
        val id: Int?, // 73457
        @SerializedName("known_for_department")
        val knownForDepartment: String?, // Acting
        @SerializedName("name")
        val name: String?, // Chris Pratt
        @SerializedName("order")
        val order: Int?, // 0
        @SerializedName("original_name")
        val originalName: String?, // Chris Pratt
        @SerializedName("popularity")
        val popularity: Double?, // 53.988
        @SerializedName("profile_path")
        val profilePath: String? // /83o3koL82jt30EJ0rz4Bnzrt2dd.jpg
    )

    data class Crew(
        @SerializedName("adult")
        val adult: Boolean?, // false
        @SerializedName("credit_id")
        val creditId: String?, // 5f75c04821621b0033319b71
        @SerializedName("department")
        val department: String?, // Art
        @SerializedName("gender")
        val gender: Int?, // 2
        @SerializedName("id")
        val id: Int?, // 1653447
        @SerializedName("job")
        val job: String?, // Storyboard Artist
        @SerializedName("known_for_department")
        val knownForDepartment: String?, // Art
        @SerializedName("name")
        val name: String, // Andrew Gaskill
        @SerializedName("original_name")
        val originalName: String?, // Andrew Gaskill
        @SerializedName("popularity")
        val popularity: Double?, // 5.04
        @SerializedName("profile_path")
        val profilePath: String? // /2ItA4bGkHMC11Z7JSJAyPwALHXD.jpg
    )
}