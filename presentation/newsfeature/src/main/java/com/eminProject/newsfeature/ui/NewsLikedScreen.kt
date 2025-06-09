package com.eminProject.newsfeature.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.eminProject.model.news.dto.NewsDetailArgs
import com.eminProject.newsfeature.navigation.navigateNewsDetailScreen
import com.eminProject.newsfeature.ui.components.NewsListColumn
import com.eminProject.newsfeature.viewmodel.NewsViewModel
import com.eminProject.utils.Constants.BACK

@RequiresApi(Build.VERSION_CODES.O)
@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun NewsLikedScreen(navController: NavController, viewModel: NewsViewModel = hiltViewModel(), onBackClick: () -> Unit) {
    val likedNews = viewModel.likedNews

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Favorite News",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Blue
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = BACK
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            if (likedNews.isEmpty()) {
                Text("Not Liked bews")
            } else {
                LazyColumn {
                    items(likedNews) { news ->
                        NewsListColumn(
                            news = news,
                            onItemClick = {
                                navController.navigateNewsDetailScreen(
                                    NewsDetailArgs(
                                        news.title,
                                        news.image_url,
                                        news.summary,
                                        news.authors,
                                        news.published_at,
                                        news.updated_at,
                                        news.news_site,
                                        news.url
                                    )
                                )
                            },
                            onLikeClick = { clickedNews ->
                                viewModel.removeLikedNews(clickedNews)
                                viewModel.toggleLike(clickedNews)
                            }
                        )
                    }
                }
            }
        }
    }
}
