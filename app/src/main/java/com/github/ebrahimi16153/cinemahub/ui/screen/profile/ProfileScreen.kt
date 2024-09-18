package com.github.ebrahimi16153.cinemahub.ui.screen.profile

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.github.ebrahimi16153.cinemahub.R


@Composable
fun ProfileScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            Image( modifier = Modifier.size(200.dp).clip(CircleShape), painter = painterResource(id = R.drawable.ebrahimi16153), contentDescription ="" )
            Spacer(modifier = Modifier.height(10.dp))
                val context  = LocalContext.current
                Text(modifier = Modifier.wrapContentWidth().clickable {
                    val url = "https://github.com/ebrahimi16153"
                    val i = Intent(Intent.ACTION_VIEW)
                    i.setData(Uri.parse(url))
                    context.startActivity(i)
                }, text = "develop by @ebrahimi16153", style = MaterialTheme.typography.bodyMedium)

            }

    }
}