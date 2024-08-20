package com.github.ebrahimi16153.cinemahub.data.model

import com.google.gson.annotations.SerializedName

data class Genre(
    @SerializedName("id")
    val id: Int, // 28
    @SerializedName("name")
    val name: String? // Action
)