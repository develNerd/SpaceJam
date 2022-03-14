package jc.iakakpo.spacejam.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.AsyncImage
import jc.iakakpo.spacejam.R
import jc.iakakpo.spacejam.RocketLoader
import jc.iakakpo.spacejam.models.PastLaunchesData
import jc.iakakpo.spacejam.ui.theme.*
import jc.iakakpo.spacejam.ui.viewmodel.PastLaunchesViewModel
import jc.iakakpo.spacejam.utils.HandleLoadingMoreData
import jc.iakakpo.spacejam.utils.UIState
import jc.iakakpo.spacejam.utils.randoms

/**
 * @author Isaac Akakpo
 * Created on 3/1/2022 9:32 AM
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PastLaunchesPage(viewModel: PastLaunchesViewModel, navController: NavController) {

  Scaffold(topBar = {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = spaceLightGreen), verticalAlignment = Alignment.CenterVertically
    ) {
      IconButton(onClick = {
        navController.popBackStack()
      }) {
        Icon(painterResource(id = R.drawable.ic_back), contentDescription = "")
      }
      Text(
          text = "Past Launches",
          fontSize = titleTextSize,
          modifier = Modifier.padding(vertical = dp20)
      )
    }
  }, backgroundColor = backGroundColor()) {

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {


      /* val pastLaunchesState by remember(key1 = viewModel) { viewModel.getPastLaunches() }
           .collectAsState(initial = UIState.Loading)*/
      var manualRefresh by remember {
        mutableStateOf(false)
      }
      val paginatedPastLaunches = viewModel.paginatedPastLaunches.collectAsLazyPagingItems()

      LazyColumn(modifier = Modifier.fillMaxSize()) {

        items(paginatedPastLaunches) { launches ->
          PastLaunchesItem(launches?.launch_year, launches?.missionName, launches?.links?.flickerImages
              ?: emptyList<String>())
        }
        //
        item() {
          HandleLoadingMoreData(stateView = {
            PageLoader(modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp))
          }, errorView = {
            ErrorView(this@BoxWithConstraints) {
              paginatedPastLaunches.retry()
            }
          }, pagingItems = paginatedPastLaunches,manualRefresh,{ manualRefresh = it })
        }

      }


/*
      paginatedPastLaunches.apply {
        when (this) {
          is UIState.DataLoaded -> {




          }
          UIState.Loading -> {
            RocketLoader(modifier = Modifier.align(Alignment.Center))
          }
          is UIState.NoInternet -> {
            Column(modifier = Modifier.align(Alignment.Center), horizontalAlignment = Alignment.CenterHorizontally) {
              Text(text = "Something Went Wrong", modifier = Modifier.align(Alignment.CenterHorizontally))
              OutlinedButton(onClick = { pastLaunchesState.re }) {
                Text(text = "Retry", modifier = Modifier.padding(horizontal = 15.dp))
              }
            }
          }
          UIState.SomethingWentWrong -> {
            Text(text = "Something Went Wrong", modifier = Modifier.align(Alignment.Center))
          }

        }
      }
*/


    }
  }

}



@Composable
fun ErrorView(boxWithConstraintsScope: BoxWithConstraintsScope, onClick: () -> Unit) {
  Column(modifier = Modifier
      .height(boxWithConstraintsScope.maxHeight)
      .fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
    Image(painter = painterResource(id = R.drawable.error_img), contentDescription = "", modifier = Modifier.size(200.dp))
    Text(text = "oops.. Something Went Wrong",modifier = Modifier.padding(top = 30.dp))
    OutlinedButton(onClick = { onClick() }, border = BorderStroke(2.dp, color = spaceGreenDark), modifier = Modifier.padding(top = 30.dp)) {
      Text(text = "Retry", modifier = Modifier.padding(horizontal = 15.dp))
    }
  }

}

@Composable
fun PastLaunchesItem(launchYear: String?, missionName: String?, images: List<String?>) {
  Box(
      modifier = Modifier
          .fillMaxWidth()
          .padding(5.dp)
          .background(color = spaceGreenTransluscent, shape = RoundedCornerShape(8))
  ) {

    val url = if (images.isNullOrEmpty()) randoms.random() else images[0]
    Column(verticalArrangement = Arrangement.spacedBy(6.dp), modifier = Modifier.padding(10.dp)) {


      AsyncImage(
          model = url,
          contentDescription = null,
          modifier = Modifier
              .fillMaxWidth()
              .height(100.dp)
              .clip(RoundedCornerShape(10))
              .background(shape = RoundedCornerShape(10), color = spaceGreenLight),
          contentScale = ContentScale.Crop
      )
      ListItemMainText(text = missionName ?: "")
      ListItemSupportText(text = launchYear ?: "")
    }

  }
}



