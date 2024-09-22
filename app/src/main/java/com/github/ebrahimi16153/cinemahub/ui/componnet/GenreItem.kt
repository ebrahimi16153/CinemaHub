package com.github.ebrahimi16153.cinemahub.ui.componnet

import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.github.ebrahimi16153.cinemahub.data.model.Genre


///////////////////////////Genre Item//////////////////////
@Composable
fun GenreItem(
    isSelected: Boolean,
    genre: Genre,
    onGenreClick: (Genre) -> Unit
) {
    OutlinedButton(
        colors = ButtonDefaults.buttonColors()
            .copy(
                containerColor =
                if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent,
                contentColor = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onBackground
            ),

        onClick = { onGenreClick(genre) },
        //selected genre
        border = BorderStroke(
            width = if (isSelected) 3.dp else 1.dp,
            color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground
        )
    ) {
        genre.name?.let { Text(text = it) }

    }
}