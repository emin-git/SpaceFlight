package com.emin.newsfeature.navigation

import NewsScreen
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresExtension
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.emin.model.news.dto.NewsDetailArgs
import com.emin.newsfeature.ui.NewsDetailScreen
import com.emin.newsfeature.ui.NewsLikedScreen
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets


@Serializable
data object News

@Serializable
data object NewsNavGraph

@Serializable
data object Liked

@RequiresApi(Build.VERSION_CODES.O)
@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
fun NavGraphBuilder.newsScreen(navController: NavHostController) {
    composable<News> {
        NewsScreen(navController)
    }
}

fun NavGraphBuilder.newsNavGraph(
    nestedNavGraphs: NavGraphBuilder.() -> Unit
) {
    navigation<NewsNavGraph>(startDestination = News) {
        nestedNavGraphs()
    }
}

fun NavController.navigateNewsDetailScreen(args: NewsDetailArgs) {
    val json = Json.encodeToString(args)
    val encodedJson = URLEncoder.encode(json, StandardCharsets.UTF_8.toString())
    this.navigate("newsDetail/$encodedJson")
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.newsDetailScreen(navController: NavController) {
    composable(
        route = "newsDetail/{args}",
        arguments = listOf(navArgument("args") { type = NavType.StringType })
    ) { backStackEntry ->
        val encodedArgs = backStackEntry.arguments?.getString("args")
        val json = URLDecoder.decode(encodedArgs ?: "", StandardCharsets.UTF_8.toString())
        val args = Json.decodeFromString<NewsDetailArgs>(json)

        NewsDetailScreen(newsDetailArgs = args, onBackClick = { navController.popBackStack() })
    }
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
fun NavController.navigateLikedScreen() {
    navigate(Liked)
}

@RequiresApi(Build.VERSION_CODES.O)
@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
fun NavGraphBuilder.newsLikedScreen(navController:NavController ){
    composable<Liked>{
        NewsLikedScreen(navController, onBackClick = { navController.popBackStack() })
    }
}
