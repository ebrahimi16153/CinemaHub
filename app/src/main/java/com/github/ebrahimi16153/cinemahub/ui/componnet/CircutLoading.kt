package com.github.ebrahimi16153.cinemahub.ui.componnet

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun  MyCircularProgress(){
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

        CircularProgressIndicator(modifier = Modifier.size(36.dp))
    }
}