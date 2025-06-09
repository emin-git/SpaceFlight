package com.eminProject.newsfeature.ui

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.eminProject.common.extensions.dateExtension
import com.eminProject.model.news.Author
import com.eminProject.model.news.Socials
import com.eminProject.model.news.dto.NewsDetailArgs
import com.eminProject.newsfeature.ui.components.ClickablePlatformText
import com.eminProject.utils.Constants.AUTHOR
import com.eminProject.utils.Constants.BACK
import com.eminProject.utils.Constants.NEWS
import com.eminProject.utils.Constants.READ_NEWS

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NewsDetailScreen(newsDetailArgs: NewsDetailArgs, onBackClick: () -> Unit) {
    val scrollState = rememberScrollState()
    val context = LocalContext.current

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = NEWS,
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
        },
        containerColor = Color.White
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(paddingValues)
                .background(Color.White)
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
        ) {

            newsDetailArgs.imageUrl?.let { imageUrl ->
                Image(
                    painter = rememberAsyncImagePainter(imageUrl),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .shadow(4.dp),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(24.dp))
            }

            newsDetailArgs.title?.let { title ->
                Text(
                    text = title,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                newsDetailArgs.publishedAt?.let {
                    Text(
                        text = "📅 ${dateExtension(it)}",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }

                newsDetailArgs.newsSite?.let {
                    Text(
                        text = "📰 $it",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            newsDetailArgs.summary?.let {
                Text(
                    text = it,
                    fontSize = 16.sp,
                    color = Color.DarkGray,
                    lineHeight = 22.sp
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            if (!newsDetailArgs.authors.isNullOrEmpty()) {
                Text(
                    text = AUTHOR,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                newsDetailArgs.authors?.let { authors ->
                    val hasSocialLinks = authors.any { author ->
                        author.socials?.let { socials ->
                            socials.linkedin != null || socials.instagram != null || socials.youtube != null || socials.x != null
                        } ?: false
                    }
                    if (hasSocialLinks) {
                        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            itemsIndexed(authors) { index, author ->
                                Card(
                                    shape = RoundedCornerShape(12.dp),
                                    elevation = CardDefaults.cardElevation(4.dp),
                                    modifier = Modifier
                                ) {
                                    Column(
                                        modifier = Modifier.padding(8.dp),
                                        verticalArrangement = Arrangement.Center
                                    ) {
                                        author.socials?.let { socials ->
                                            socials.linkedin?.let { linkedinUrl ->
                                                ClickablePlatformText(
                                                    platformName = "LinkedIn",
                                                    url = linkedinUrl,
                                                    context = context
                                                )
                                            }
                                            socials.instagram?.let { instagramUrl ->
                                                ClickablePlatformText(
                                                    platformName = "Instagram",
                                                    url = instagramUrl,
                                                    context = context
                                                )
                                            }
                                            socials.youtube?.let { youtubeUrl ->
                                                ClickablePlatformText(
                                                    platformName = "YouTube",
                                                    url = youtubeUrl,
                                                    context = context
                                                )
                                            }
                                            socials.x?.let { xUrl ->
                                                ClickablePlatformText(
                                                    platformName = "X",
                                                    url = xUrl,
                                                    context = context
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
            }

            newsDetailArgs.url?.let { url ->
                Button(
                    onClick = {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        context.startActivity(intent)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(Color.Blue)
                ) {
                    Text(text = READ_NEWS, fontSize = 16.sp)
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
@Preview
fun PreviewNewsDetailScreen() {
    NewsDetailScreen(
        newsDetailArgs = NewsDetailArgs(
            "title",
            "imageUrl",
            "summary",
            listOf(Author("sandra", Socials("x", "youtube", "instagram", "linkedn", null, null))),
            "publishedAt",
            "updatedAt",
            "newsSite",
            "url"
        ), onBackClick = {}
    )
}