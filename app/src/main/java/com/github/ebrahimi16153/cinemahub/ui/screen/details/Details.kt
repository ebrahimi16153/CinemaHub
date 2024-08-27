package com.github.ebrahimi16153.cinemahub.ui.screen.details

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.github.ebrahimi16153.cinemahub.data.model.ImageCollection
import com.github.ebrahimi16153.cinemahub.utils.IMAGE_URL

@Composable
fun Details(movieID: Int) {

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        Text(text = "$movieID")
    }


    MovieBanner()

}

@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
fun MovieBanner(
    posters:List<ImageCollection.Poster> = emptyList(),
    onPosterClick:(String) -> Unit = {}
) {
   val state = rememberPagerState(pageCount = {posters.size})
    HorizontalPager(modifier = Modifier.fillMaxWidth(), state = state ) {itIndex ->
        PosterItems(posters[itIndex])
    }



}

@Composable
fun PosterItems(poster: ImageCollection.Poster) {
    AsyncImage(modifier = Modifier.fillMaxWidth(), model = IMAGE_URL+poster.filePath, contentDescription ="" )
}
