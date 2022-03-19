package jc.iakakpo.spacejam

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
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
import jc.iakakpo.spacejam.ui.screens.companydetails.CompanyDetailsViewModel
import jc.iakakpo.spacejam.ui.screens.companydetails.Details
import jc.iakakpo.spacejam.ui.screens.pastlaunches.PastLaunchesPage
import jc.iakakpo.spacejam.ui.screens.pastlaunches.PastLaunchesViewModel
import jc.iakakpo.spacejam.ui.theme.SpaceJamTheme
import jc.iakakpo.spacejam.ui.theme.backGroundColor
import jc.iakakpo.spacejam.ui.theme.dp10
import jc.iakakpo.spacejam.ui.theme.spaceLightGreen

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
                    composable(Pages.COMPANY_DETAILS.pageName) {
                        val companyDetailsViewModel: CompanyDetailsViewModel = hiltViewModel<CompanyDetailsViewModel>()
                        Details(navController, companyDetailsViewModel)
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
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = spaceLightGreen)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_space_icon),
                    contentDescription = "",
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .wrapContentWidth()
                        .padding(20.dp)
                        .height(16.dp)
                )

                var expanded by remember {
                    mutableStateOf(false)
                }

                Box(modifier = Modifier.padding(end = dp10).align(Alignment.CenterEnd)) {
                    IconButton(onClick = {
                        expanded = !expanded
                    }, modifier = Modifier.align(Alignment.CenterEnd)) {
                        Icon(Icons.Filled.MoreVert, contentDescription = "")
                    }
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },Modifier.align(Alignment.BottomEnd)
                    ) {
                        DropdownMenuItem(onClick = {
                            expanded = false
                            navController.navigate(Pages.COMPANY_DETAILS.pageName) }) {
                            Text("Company Details")
                        }
                    }
                }


                // DropDown


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
