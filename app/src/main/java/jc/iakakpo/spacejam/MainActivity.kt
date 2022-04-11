package jc.iakakpo.spacejam

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import jc.iakakpo.spacejam.enums.StringResourceProvider
import jc.iakakpo.spacejam.ui.screens.MainDashboardItem
import jc.iakakpo.spacejam.ui.screens.companydetails.CompanyDetailsViewModel
import jc.iakakpo.spacejam.ui.screens.companydetails.Details
import jc.iakakpo.spacejam.ui.screens.launchPads.LandPadViewModel
import jc.iakakpo.spacejam.ui.screens.launchPads.LandPads
import jc.iakakpo.spacejam.ui.screens.missions.Missions
import jc.iakakpo.spacejam.ui.screens.missions.MissionsViewModel
import jc.iakakpo.spacejam.ui.screens.pastlaunches.PastLaunchesPage
import jc.iakakpo.spacejam.ui.screens.pastlaunches.PastLaunchesViewModel
import jc.iakakpo.spacejam.ui.screens.ships.Ships
import jc.iakakpo.spacejam.ui.screens.ships.ShipsViewModel
import jc.iakakpo.spacejam.ui.theme.*
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    @Inject
    lateinit var stringProvider: StringResourceProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            SpaceJamTheme {

                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = stringProvider.mainPage) {

                    composable(stringProvider.mainPage) { SpaceMainPage(navController, stringProvider = stringProvider) }
                    composable(stringProvider.pastLaunches) {
                        val pastLaunchesViewModel = hiltViewModel<PastLaunchesViewModel>()
                        PastLaunchesPage(pastLaunchesViewModel, navController)
                    }
                    composable(stringProvider.ships){
                        val shipsViewModel = hiltViewModel<ShipsViewModel>()
                        Ships(navController = navController, shipsViewModel = shipsViewModel, title = stringProvider.ships)
                    }
                    composable(stringProvider.companyDetails) {
                        val companyDetailsViewModel: CompanyDetailsViewModel = hiltViewModel<CompanyDetailsViewModel>()
                        Details(navController, companyDetailsViewModel)
                    }
                    composable(stringProvider.missions) {
                        val missionsViewModel = hiltViewModel<MissionsViewModel>()
                        Missions(navController, missionsViewModel,stringProvider.missions)
                    }
                    composable(stringProvider.landPads){
                        val landPadViewModel:LandPadViewModel = hiltViewModel()
                        LandPads(navController = navController, landPadViewModel = landPadViewModel , title = stringProvider.landPads)
                    }

                    /*...*/
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SpaceMainPage(navController: NavController, stringProvider: StringResourceProvider) {
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

                Box(
                    modifier = Modifier
                        .padding(end = dp10)
                        .align(Alignment.CenterEnd)
                ) {
                    IconButton(onClick = {
                        expanded = !expanded
                    }, modifier = Modifier.align(Alignment.CenterEnd)) {
                        Icon(Icons.Filled.MoreVert, contentDescription = "")
                    }
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }, Modifier.align(Alignment.BottomEnd)
                    ) {
                        DropdownMenuItem(onClick = {
                            expanded = false
                            navController.navigate(stringProvider.companyDetails)
                        }) {
                            Text(stringProvider.companyDetails)
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
                MainDashboardItem(stringProvider.pastLaunches, R.drawable.ic_past_launches, this) {
                    navController.navigate(stringProvider.pastLaunches)
                }
                MainDashboardItem("Ships", R.drawable.ic_ships, this) {
                    navController.navigate(stringProvider.ships)
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                horizontalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                MainDashboardItem(stringProvider.missions, R.drawable.ic_next_launches, this) {
                    navController.navigate(stringProvider.missions)
                }
                MainDashboardItem(stringProvider.landPads, R.drawable.ic_lauchpad, this) {
                    navController.navigate(stringProvider.landPads)
                }
            }
        }
    }
}
