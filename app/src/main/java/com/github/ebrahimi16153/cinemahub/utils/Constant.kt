package com.github.ebrahimi16153.cinemahub.utils

const val CONNECTION_TIMEOUT = 25L
const val API_KEY = "444a3900eac59a6892d47a7250a984f5"
const val BASE_URL = "https://api.themoviedb.org/3/"
const val IMAGE_URL = "https://image.tmdb.org/t/p/w500"

val BOTTOM_ITEMS = listOf(Route.Home,Route.Search,Route.Saved,Route.Profile)

const val MOVIE_TABLE = "movie_table"
const val APP_DATABASE  = "app_database"


fun Int.toHours(mint:Double): String {
    val doubleHours = mint/60
    val hours  = doubleHours.toInt()
    val m = (doubleHours - hours) * 60

    return "$hours: ${m.toInt()}"

}