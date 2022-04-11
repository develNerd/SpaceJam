package jc.iakakpo.spacejam.ui.screens.ships

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import jc.iakakpo.spacejam.ShipsQuery
import jc.iakakpo.spacejam.ui.screens.missions.MissionItem
import jc.iakakpo.spacejam.ui.screens.viewhelpers.RetryView
import jc.iakakpo.spacejam.ui.screens.viewhelpers.RocketLoader
import jc.iakakpo.spacejam.ui.screens.viewhelpers.Toolbar
import jc.iakakpo.spacejam.ui.theme.*
import jc.iakakpo.spacejam.utils.UIState

/**
 * @author Isaac Akakpo
 * Created on 3/29/2022 4:34 PM
 */

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Ships(navController: NavController, shipsViewModel: ShipsViewModel, title: String) {

  Scaffold(topBar = {
    Toolbar(title = title, showDropDown = false) {
      navController.popBackStack()
    }
  }) {

    val missionsState by remember { shipsViewModel.getShips() }
      .collectAsState(UIState.Loading)

    Box(modifier = Modifier.fillMaxSize()) {
      val state = rememberLazyListState()
      missionsState.apply {
        when (this) {
          is UIState.DataLoaded -> {
            // Get thumbnail of spaceX website
            LazyVerticalGrid(verticalArrangement = Arrangement.spacedBy(dp3), state = state, cells = GridCells.Fixed(2)) {
              items(response.ships!!) {  ship ->
                ShipsItem(ship = ship!!)
              }
            }
          }
          is UIState.Loading -> {
            Column(
              modifier = Modifier
                .wrapContentSize()
                .align(Alignment.Center),
              horizontalAlignment = Alignment.CenterHorizontally
            ) {
              RocketLoader()
            }
          }
          is UIState.NoInternet -> {
            RetryView(boxScope = this@Box) {
              shipsViewModel.retry()
            }
          }
          is UIState.SomethingWentWrong -> {
            RetryView(boxScope = this@Box) {
              shipsViewModel.retry()
            }
          }
        }
      }
    }


  }


}

@Composable
fun ShipsItem(ship: ShipsQuery.Ship) {

  Box(
    modifier = Modifier
      .fillMaxWidth()
      .padding(5.dp)
      .background(color = spaceGreenTransluscent, shape = RoundedCornerShape(8))
  ) {

    Column(verticalArrangement = Arrangement.spacedBy(6.dp), modifier = Modifier.padding(10.dp)) {

      Box(
        modifier = Modifier
          .fillMaxWidth()
          .height(100.dp)
          .clip(RoundedCornerShape(10))
          .background(shape = RoundedCornerShape(10), color = spaceGreenLighter),
      ) {
        AsyncImage(
          model = ship.image,
          contentDescription = null,
          modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .clip(RoundedCornerShape(10))
            .background(shape = RoundedCornerShape(10), color = spaceGreenLight),
          contentScale = ContentScale.Crop
        )
      }

      Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Text(
          text = ship.name ?: "",
          fontSize = listItemMainTextSize,
          color = Color.Black,
          fontWeight = FontWeight.Bold,
          modifier = Modifier.weight(1F)
        )
      }
    }
  }
}