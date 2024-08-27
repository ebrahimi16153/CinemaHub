package com.github.ebrahimi16153.cinemahub.data.model

import com.google.gson.annotations.SerializedName

data class MovieDetail(
    @SerializedName("adult")
    val adult: Boolean?, // false
    @SerializedName("backdrop_path")
    val backdropPath: String?, // /rrwt0u1rW685u9bJ9ougg5HJEHC.jpg
    @SerializedName("belongs_to_collection")
    val belongsToCollection: BelongsToCollection?,
    @SerializedName("budget")
    val budget: Int?, // 150000000
    @SerializedName("genres")
    val genres: List<Genre>,
    @SerializedName("homepage")
    val homepage: String?, // https://www.netflix.com/title/81076856
    @SerializedName("id")
    val id: Int, // 280180
    @SerializedName("imdb_id")
    val imdbId: String?, // tt3083016
    @SerializedName("origin_country")
    val originCountry: List<String?>?,
    @SerializedName("original_language")
    val originalLanguage: String?, // en
    @SerializedName("original_title")
    val originalTitle: String?, // Beverly Hills Cop: Axel F
    @SerializedName("overview")
    val overview: String?, // Forty years after his unforgettable first case in Beverly Hills, Detroit cop Axel Foley returns to do what he does best: solve crimes and cause chaos.
    @SerializedName("popularity")
    val popularity: Double?, // 1214.968
    @SerializedName("poster_path")
    val posterPath: String?, // /zszRKfzjM5jltiq8rk6rasKVpUv.jpg
    @SerializedName("production_companies")
    val productionCompanies: List<ProductionCompany>,
    @SerializedName("production_countries")
    val productionCountries: List<ProductionCountry>,
    @SerializedName("release_date")
    val releaseDate: String?, // 2024-06-20
    @SerializedName("revenue")
    val revenue: Int?, // 0
    @SerializedName("runtime")
    val runtime: Int?, // 118
    @SerializedName("spoken_languages")
    val spokenLanguages: List<SpokenLanguage?>?,
    @SerializedName("status")
    val status: String?, // Released
    @SerializedName("tagline")
    val tagline: String?,
    @SerializedName("title")
    val title: String?, // Beverly Hills Cop: Axel F
    @SerializedName("video")
    val video: Boolean?, // false
    @SerializedName("vote_average")
    val voteAverage: Double?, // 6.887
    @SerializedName("vote_count")
    val voteCount: Int? // 613
) {
    data class BelongsToCollection(
        @SerializedName("backdrop_path")
        val backdropPath: String?, // /k0DEUZqbB9bVcm2NsVaMkdd4Vs8.jpg
        @SerializedName("id")
        val id: Int?, // 85861
        @SerializedName("name")
        val name: String?, // Beverly Hills Cop Collection
        @SerializedName("poster_path")
        val posterPath: String? // /whjkHIzJ0Kiei6V7z5NaIyvFKQm.jpg
    )


    data class ProductionCompany(
        @SerializedName("id")
        val id: Int?, // 10288
        @SerializedName("logo_path")
        val logoPath: String?, // /Aszf09kIVXR6cwG9lwjZIawbYVS.png
        @SerializedName("name")
        val name: String?, // Don Simpson/Jerry Bruckheimer Films
        @SerializedName("origin_country")
        val originCountry: String? // US
    )

    data class ProductionCountry(
        @SerializedName("iso_3166_1")
        val iso31661: String?, // US
        @SerializedName("name")
        val name: String? // United States of America
    )

    data class SpokenLanguage(
        @SerializedName("english_name")
        val englishName: String?, // English
        @SerializedName("iso_639_1")
        val iso6391: String?, // en
        @SerializedName("name")
        val name: String? // English
    )
}