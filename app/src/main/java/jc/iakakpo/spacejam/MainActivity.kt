package jc.iakakpo.spacejam

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import jc.iakakpo.spacejam.enums.Pages
import jc.iakakpo.spacejam.ui.screens.MainDashboardItem
import jc.iakakpo.spacejam.ui.screens.PastLaunchesPage
import jc.iakakpo.spacejam.ui.theme.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SpaceJamTheme {

                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = Pages.MAIN_PAGE.pageName) {
                    composable(Pages.MAIN_PAGE.pageName) { SpaceMainPage(navController) }
                    composable(Pages.PAST_LAUNCHES.pageName) { PastLaunchesPage() }
                    /*...*/
                }


            }
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SpaceMainPage(navController:NavController){
    Scaffold(topBar =
    {
        Row(modifier = Modifier
            .fillMaxWidth()
            .background(color = spaceLightGreen)) {
            Image(
                painter = painterResource(id = R.drawable.ic_space_icon),
                contentDescription = "",
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(20.dp)
                    .height(16.dp)
            )
        }
    }, modifier = Modifier.fillMaxSize(), backgroundColor = backGroundColor()
    )
    {
        Column(modifier = Modifier.wrapContentSize(), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Row(modifier= Modifier
                .fillMaxWidth()
                .padding(15.dp), horizontalArrangement = Arrangement.spacedBy(15.dp)) {
                MainDashboardItem("Past Launches",R.drawable.ic_past_launches,this){
                    navController.navigate(Pages.PAST_LAUNCHES.pageName)
                }
                MainDashboardItem("Ships",R.drawable.ic_ships,this){

                }
            }

            Row(modifier= Modifier
                .fillMaxWidth()
                .padding(15.dp),horizontalArrangement = Arrangement.spacedBy(15.dp)) {
                MainDashboardItem("Next Launches",R.drawable.ic_next_launches,this){

                }
                MainDashboardItem("Launch Pads",R.drawable.ic_lauchpad,this){

                }
            }
        }


    }


}






