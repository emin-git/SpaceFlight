package com.emin.newsfeature.ui.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.emin.common.extensions.dateExtension
import com.emin.model.news.Result

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NewsListColumn(
    news: Result,
    onItemClick: (Result) -> Unit,
    onLikeClick: (Result) -> Unit
) {
    val title = news.title
    val iconColor = if (news.isLiked) Color.Red else Color.Gray
    val textColor = if (title == "Batman Begins") Color.Red else Color.Black

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick(news) }
            .padding(top = 5.dp, start = 10.dp, end = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        news.title?.let { Text(text = it) }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(6.dp))
                .background(Color.Transparent)
        ) {
            AsyncImage(
                model = news.image_url,
                contentDescription = title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RectangleShape)
            )
            IconButton(
                onClick = {
                    onLikeClick(news)
                },
                modifier = Modifier.align(Alignment.TopEnd)
            ) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Favori",
                    tint = iconColor
                )
            }
        }

        title?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.headlineSmall,
                color = textColor,
                textAlign = TextAlign.Start,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp)
            )
        }

        news.summary?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.bodySmall,
                color = Color.DarkGray,
                textAlign = TextAlign.Start,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp)
            )
        }

        news.updated_at?.let {
            Text(
                text = dateExtension(it),
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp)
            )
        }

        HorizontalDivider(
            modifier = Modifier.padding(vertical = 8.dp),
            thickness = 0.5.dp,
            color = Color.DarkGray
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
@Preview
fun MovieListRowView() {
    NewsListColumn(
        news = Result(
            title = "Derek Tournear Reinstated as Director of Space Development Agency Following Investigation",
            image_url = "https://cdn.tlpnetwork.com/articles/2025/1744744796783.jpg",
            summary = "NASA and Roscosmos have extended a seat barter agreement for flights to the International Space Station into 2027 that will feature longer Soyuz missions to the station.\\nThe post NASA extends seat barter agreement with Roscosmos into 2027 appeared first on SpaceNews.",
            updated_at = "15 Nisan 2025, 19:10"
        ),
        onItemClick = {},
        onLikeClick = {})
}
