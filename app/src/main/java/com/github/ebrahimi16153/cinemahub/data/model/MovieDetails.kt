package com.github.ebrahimi16153.cinemahub.data.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.github.ebrahimi16153.cinemahub.utils.MOVIE_TABLE
import com.google.gson.annotations.SerializedName

@Entity(tableName = MOVIE_TABLE)
data class MovieDetail(
    @SerializedName("adult")
    @Ignore
    var adult: Boolean? = null, // false
    @SerializedName("backdrop_path")
    @Ignore
    var backdropPath: String? = null, // /rrwt0u1rW685u9bJ9ougg5HJEHC.jpg
    @Ignore
    @SerializedName("belongs_to_collection")
    var belongsToCollection: BelongsToCollection? = null,
    @SerializedName("budget")
    var budget: Int? = null, // 150000000
    @SerializedName("genres")
    @Ignore
    var genres: List<Genre>? = null,
    @SerializedName("homepage")
    @Ignore
    var homepage: String? = null, // https://www.netflix.com/title/81076856
    @SerializedName("id")
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0, // 280180
    @SerializedName("imdb_id")
    @Ignore
    var imdbId: String? = null, // tt3083016
    @SerializedName("origin_country")
    @Ignore
    var originCountry: List<String>? = null,
    @SerializedName("original_language")
    var originalLanguage: String? = null, // en
    @SerializedName("original_title")
    var originalTitle: String? = null, // Beverly Hills Cop: Axel F
    @SerializedName("overview")
    var overview: String? = null, // Forty years after his unforgettable first case in Beverly Hills, Detroit cop Axel Foley returns to do what he does best: solve crimes and cause chaos.
    @SerializedName("popularity")
    var popularity: Double? = null, // 1214.968
    @SerializedName("poster_path")
    var posterPath: String? = null, // /zszRKfzjM5jltiq8rk6rasKVpUv.jpg
    @SerializedName("production_companies")
    @Ignore
    var productionCompanies: List<ProductionCompany>? = null,
    @SerializedName("production_countries")
    @Ignore
    var productionCountries: List<ProductionCountry>? = null,
    @SerializedName("release_date")
    var releaseDate: String? = null, // 2024-06-20
    @SerializedName("revenue")
    var revenue: Int? = null, // 0
    @SerializedName("runtime")
    var runtime: Int? = null, // 118
    @SerializedName("spoken_languages")
    @Ignore
    var spokenLanguages: List<SpokenLanguage>? = null,
    @SerializedName("status")
    var status: String? = null, // Released
    @SerializedName("tagline")
    var tagline: String? = null,
    @SerializedName("title")
    var title: String? = null, // Beverly Hills Cop: Axel F
    @SerializedName("video")
    var video: Boolean? = null, // false
    @SerializedName("vote_average")
    var voteAverage: Double? = null, // 6.887
    @SerializedName("vote_count")
    var voteCount: Int? = null // 613
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
    ){
        override fun toString(): String {
            return "$name "
        }
    }

    data class SpokenLanguage(
        @SerializedName("english_name")
        val englishName: String?, // English
        @SerializedName("iso_639_1")
        val iso6391: String?, // en
        @SerializedName("name")
        val name: String? // English
    )
}