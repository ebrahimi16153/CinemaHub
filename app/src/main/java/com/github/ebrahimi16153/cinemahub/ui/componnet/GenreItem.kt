package com.github.ebrahimi16153.cinemahub.ui.componnet

import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.github.ebrahimi16153.cinemahub.ui.theme.CinemaHubTheme


///////////////////////////Genre Item//////////////////////
@Composable
fun GenreItem(
    text:String,
    onGenreClick:() -> Unit
){
    CinemaHubTheme {
        OutlinedButton(onClick = { onGenreClick() }) {
            Text(text = text)

        }
    }
}