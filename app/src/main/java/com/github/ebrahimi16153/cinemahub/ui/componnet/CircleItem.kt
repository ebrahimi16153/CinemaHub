package com.github.ebrahimi16153.cinemahub.ui.componnet

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun CircleItems(name: String, imagePath: String) {

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Surface(
            modifier = Modifier.size(30.dp),
            color = MaterialTheme.colorScheme.primary,
            shape = CircleShape
        ) {
            AsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = imagePath,
                contentDescription = "",
                contentScale = ContentScale.Crop
            )
        }

        Text(text = name, style = MaterialTheme.typography.bodySmall)
    }

}