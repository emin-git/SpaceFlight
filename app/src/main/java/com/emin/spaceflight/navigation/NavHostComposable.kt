import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.emin.newsfeature.navigation.NewsNavGraph
import com.emin.newsfeature.navigation.newsDetailScreen
import com.emin.newsfeature.navigation.newsLikedScreen
import com.emin.newsfeature.navigation.newsNavGraph
import com.emin.newsfeature.navigation.newsScreen
import com.google.firebase.crashlytics.FirebaseCrashlytics

@RequiresApi(Build.VERSION_CODES.O)
@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun NavHostComposable() {
    val navController = rememberNavController()
    val startDestination = NewsNavGraph::class

    LaunchedEffect(navController) {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            Log.d("NavigationLogger", "Navigating to: ${destination.route}")
            FirebaseCrashlytics.getInstance().log("Navigating to: $destination.route")
        }
    }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ){
        newsNavGraph(
            nestedNavGraphs = {
                newsScreen(navController)
            }
        )
        newsDetailScreen(navController)
        newsLikedScreen(navController)
    }
}