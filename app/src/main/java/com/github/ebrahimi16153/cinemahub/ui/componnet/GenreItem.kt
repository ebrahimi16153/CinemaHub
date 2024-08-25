package com.github.ebrahimi16153.cinemahub.ui.componnet

import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.github.ebrahimi16153.cinemahub.data.model.Genre
import com.github.ebrahimi16153.cinemahub.ui.theme.CinemaHubTheme


///////////////////////////Genre Item//////////////////////
@Composable
fun GenreItem(
    isSelected: Boolean,
    genre: Genre,
    onGenreClick: (Int) -> Unit
) {
    OutlinedButton(
        onClick = { onGenreClick(genre.id) },
        //selected genre
        border = BorderStroke(
            width = if (isSelected)3.dp else 1.dp,
            color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground
        )
    ) {
        genre.name?.let { Text(text = it) }

    }
}