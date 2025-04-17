import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.emin.newsfeature.ui.components.NewsListColumn
import com.emin.newsfeature.ui.components.NewsSearchBar
import com.emin.basefeature.components.RequestNotificationPermission
import com.emin.model.news.dto.NewsDetailArgs
import com.emin.newsfeature.navigation.navigateNewsDetailScreen
import com.emin.newsfeature.viewmodel.NewsEvent
import com.emin.newsfeature.viewmodel.NewsViewModel
import com.emin.utils.Constants.EMPTY_STRING
import com.emin.presentation.basefeature.R as BaseR
import com.emin.newsfeature.navigation.navigateLikedScreen
import com.emin.newsfeature.ui.components.CustomDatePickerDialog

@RequiresApi(Build.VERSION_CODES.O)
@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun NewsScreen(navController: NavController, viewModel: NewsViewModel = hiltViewModel()) {
    val state by viewModel.newsUiState.collectAsStateWithLifecycle()
    val isRequestInProgress by viewModel.isRequestInProgress.collectAsStateWithLifecycle()
    val listState = rememberLazyListState()
    val context = LocalContext.current

    var isFeaturedSelected by remember { mutableStateOf(false) }
    var isDateFilterVisible by remember { mutableStateOf(false) }
    var startDate by remember { mutableStateOf(EMPTY_STRING) }
    var endDate by remember { mutableStateOf(EMPTY_STRING) }
    var showStartDatePicker by remember { mutableStateOf(false) }
    var showEndDatePicker by remember { mutableStateOf(false) }
    var isFilterApplied by remember { mutableStateOf(false) }
    val newsList = state.data ?: emptyList()

    val isAtBottom by remember {
        isFilterApplied = false
        derivedStateOf {
            val lastVisibleItem = listState.layoutInfo.visibleItemsInfo.lastOrNull()
            lastVisibleItem?.index == listState.layoutInfo.totalItemsCount - 1 &&
                    listState.layoutInfo.totalItemsCount > 0
        }
    }

    LaunchedEffect(isAtBottom) {
        if (isAtBottom && !viewModel.isRequestInProgress.value) {
            viewModel.loadMoreNews()
        }
    }

    LaunchedEffect(state.warning, isRequestInProgress) {
        if (isRequestInProgress && state.warning != null) {
            state.warning?.getContentIfNotHandled()?.let { warningMessage ->
                Toast.makeText(context, warningMessage, Toast.LENGTH_LONG).show()
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .testTag("MSBox")
    ) {

        Column {
            Box(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
            ) {

                IconButton(
                    onClick = {
                        navController.navigateLikedScreen()
                    },
                    modifier = Modifier.align(Alignment.TopStart)
                ) {
                    Icon(
                        imageVector = Icons.Filled.FavoriteBorder,
                        contentDescription = "Saved",
                    )
                }

                AsyncImage(
                    model = BaseR.drawable.nsf,
                    contentDescription = EMPTY_STRING,
                    modifier = Modifier
                        .size(width = 100.dp, height = 50.dp)
                        .clip(RectangleShape)
                        .align(Alignment.Center)
                )

                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                ) {
                    RequestNotificationPermission()
                }
            }

            NewsSearchBar(modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp, start = 10.dp, end = 10.dp),
                hint = "Searching News...",
                onSearch = {
                    viewModel.onEvent(
                        NewsEvent.Request(
                            limit = 40,
                            ordering = null,
                            search = it,
                            is_featured = isFeaturedSelected,
                            published_at_gte = startDate,
                            published_at_lte = endDate
                        )
                    )
                }
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = {
                        isFilterApplied = true
                        isFeaturedSelected = isFeaturedSelected.not()
                        viewModel.onEvent(
                            NewsEvent.Request(
                                limit = 40,
                                ordering = if (isFeaturedSelected) listOf("-published_at") else null,
                                is_featured = isFeaturedSelected,
                                published_at_gte = startDate,
                                published_at_lte = endDate,
                                search = null
                            )
                        )
                    },
                    colors = ButtonDefaults.buttonColors(if (isFeaturedSelected) Color.Blue else Color.White),
                    border = BorderStroke(
                        width = 1.dp,
                        color = Color.DarkGray
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 5.dp)
                ) {
                    Text(
                        text = "Featured",
                        color = if (isFeaturedSelected) Color.White else Color.Black
                    )
                }

                Button(
                    onClick = {
                        isDateFilterVisible = !isDateFilterVisible
                    },
                    colors = ButtonDefaults.buttonColors(if (isDateFilterVisible) Color.Blue else Color.White),
                    border = BorderStroke(
                        width = 1.dp,
                        color = Color.DarkGray
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 5.dp)
                ) {
                    Text(
                        text = "Date Filter",
                        color = if (isDateFilterVisible) Color.White else Color.Black
                    )
                }
            }

            if (isDateFilterVisible) {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 10.dp, vertical = 10.dp)
                        .fillMaxWidth(),
                ) {
                    OutlinedTextField(
                        value = startDate,
                        onValueChange = {},
                        label = { Text("Start Date (YYYY-MM-DD)") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { showStartDatePicker = true },
                        readOnly = true,
                        enabled = false
                    )

                    OutlinedTextField(
                        value = endDate,
                        onValueChange = { },
                        label = { Text("End Date (YYYY-MM-DD)") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { showEndDatePicker = true },
                        readOnly = true,
                        enabled = false

                    )

                    Spacer(modifier = Modifier.height(10.dp))
                    Button(
                        onClick = {
                            isFilterApplied = true
                            viewModel.onEvent(
                                NewsEvent.Request(
                                    limit = 40,
                                    ordering = if (isFeaturedSelected) listOf("-published_at") else null,
                                    is_featured = isFeaturedSelected,
                                    published_at_gte = startDate,
                                    published_at_lte = endDate,
                                    search = null
                                )
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(Color.Blue),
                    ) {
                        Text("Apply Date Filter")
                    }
                }
            }
            if (showStartDatePicker) {
                CustomDatePickerDialog(
                    onDateSelected = { startDate = it },
                    onDismiss = { showStartDatePicker = false }
                )
            }

            if (showEndDatePicker) {
                CustomDatePickerDialog(
                    onDateSelected = { endDate = it },
                    onDismiss = { showEndDatePicker = false }
                )
            }
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .testTag("MSLazyColumn"),
                state = listState
            ) {
                items(newsList) { news ->
                    NewsListColumn(news = news, onItemClick = {
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
                    }, onLikeClick = { clickedNews ->
                        viewModel.toggleLike(clickedNews)
                        if (news.isLiked.not()) {
                            viewModel.saveLikedNews(news)
                        } else {
                            viewModel.removeLikedNews(news)
                        }
                    })
                }
            }
        }

        state.error.peekContent()?.message?.let { it1 ->
            Text(
                text = it1,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp)
                    .align(Alignment.Center)
            )
        }

        //TextCrash(state.error.peekContent()?.message)// Crashlytics test icin acabilirsiniz.

        if (state.loading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}

@Composable
fun TextCrash(message: String?) {
    Text(
        text = message!!,
        color = MaterialTheme.colorScheme.error,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(14.dp)
    )
}