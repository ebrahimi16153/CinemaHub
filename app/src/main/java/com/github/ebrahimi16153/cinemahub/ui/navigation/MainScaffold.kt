package com.github.ebrahimi16153.cinemahub.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.github.ebrahimi16153.cinemahub.ui.componnet.MyNavigationBar

@Composable
fun MainScaffold(navController: NavHostController){

    Scaffold(
        bottomBar = {
            MyNavigationBar(navController = navController)
        }

    ) {itPadding ->
        Surface(modifier = Modifier
            .fillMaxSize()
            .padding(itPadding)) {
            //content
            Navigation(navHostController = navController)

        }
    }

}