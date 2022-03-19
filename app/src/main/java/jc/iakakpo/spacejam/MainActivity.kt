package jc.iakakpo.spacejam

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import jc.iakakpo.spacejam.enums.Pages
import jc.iakakpo.spacejam.ui.screens.MainDashboardItem
import jc.iakakpo.spacejam.ui.screens.pastlaunches.PastLaunchesPage
import jc.iakakpo.spacejam.ui.theme.SpaceJamTheme
import jc.iakakpo.spacejam.ui.theme.backGroundColor
import jc.iakakpo.spacejam.ui.theme.spaceLightGreen
import jc.iakakpo.spacejam.ui.screens.pastlaunches.PastLaunchesViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            SpaceJamTheme {

                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = Pages.MAIN_PAGE.pageName) {

                    composable(Pages.MAIN_PAGE.pageName) { SpaceMainPage(navController) }
                    composable(Pages.PAST_LAUNCHES.pageName) {
                        val pastLaunchesViewModel = hiltViewModel<PastLaunchesViewModel>()
                        PastLaunchesPage(pastLaunchesViewModel, navController)
                    }
                    composable(Pages.PAST_LAUNCHES.pageName) {
                        val pastLaunchesViewModel = hiltViewModel<PastLaunchesViewModel>()
                        PastLaunchesPage(pastLaunchesViewModel, navController)
                    }
                    /*...*/
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SpaceMainPage(navController: NavController) {
    Scaffold(
        topBar =
        {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = spaceLightGreen)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_space_icon),
                    contentDescription = "",
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(20.dp)
                        .height(16.dp)
                )
            }
        },
        modifier = Modifier.fillMaxSize(), backgroundColor = backGroundColor()
    ) {
        Column(
            modifier = Modifier
                .wrapContentSize()
                .padding(5.dp),
            verticalArrangement = Arrangement.spacedBy(3.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                horizontalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                MainDashboardItem("Past Launches", R.drawable.ic_past_launches, this) {
                    navController.navigate(Pages.PAST_LAUNCHES.pageName)
                }
                MainDashboardItem("Ships", R.drawable.ic_ships, this) {
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                horizontalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                MainDashboardItem("Next Launches", R.drawable.ic_next_launches, this) {
                }
                MainDashboardItem("Launch Pads", R.drawable.ic_lauchpad, this) {
                }
            }
        }
    }
}
