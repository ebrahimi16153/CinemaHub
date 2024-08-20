package com.github.ebrahimi16153.cinemahub.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.github.ebrahimi16153.cinemahub.ui.navigation.MainScaffold
import com.github.ebrahimi16153.cinemahub.ui.theme.CinemaHubTheme

class MainActivity : ComponentActivity() {

        

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navHostController = rememberNavController()
            CinemaHub(navHostController = navHostController)

            }
        }
}

@Composable
fun CinemaHub(navHostController: NavHostController){
    CinemaHubTheme {
        MainScaffold(navController = navHostController)
    }
}