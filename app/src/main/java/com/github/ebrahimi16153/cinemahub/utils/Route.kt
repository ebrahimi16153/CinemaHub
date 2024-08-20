package com.github.ebrahimi16153.cinemahub.utils

import android.graphics.drawable.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.AutoAwesome
import androidx.compose.material.icons.rounded.Bookmark
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.vector.ImageVector

enum class Route(val icon:ImageVector) {
    Home(icon = Icons.Rounded.Home ),
    Search(Icons.Rounded.Search),
    Discover(Icons.Rounded.AutoAwesome),
    Saved(Icons.Rounded.Bookmark),
    Profile(Icons.Rounded.AccountCircle),
    Details(Icons.Rounded.Info)
}