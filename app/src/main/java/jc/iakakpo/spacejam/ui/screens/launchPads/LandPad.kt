package jc.iakakpo.spacejam.ui.screens.launchPads

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import jc.iakakpo.spacejam.LandpadsQuery
import jc.iakakpo.spacejam.R
import jc.iakakpo.spacejam.ui.screens.missions.MissionsViewModel
import jc.iakakpo.spacejam.ui.screens.viewhelpers.RetryView
import jc.iakakpo.spacejam.ui.screens.viewhelpers.RocketLoader
import jc.iakakpo.spacejam.ui.screens.viewhelpers.Toolbar
import jc.iakakpo.spacejam.ui.theme.*
import jc.iakakpo.spacejam.utils.UIState
import kotlinx.coroutines.launch

/**
 * @author Isaac Akakpo
 * Created on 3/29/2022 6:46 PM
 */

@Composable
fun LandPads(navController: NavController, landPadViewModel: LandPadViewModel, title: String) {

  val getLandPadState by remember { landPadViewModel.getLaunchPads() }
    .collectAsState(UIState.Loading)

  val scaffoldState = rememberScaffoldState()
  val listState = rememberLazyListState()

  Scaffold(scaffoldState =scaffoldState,topBar = {
    Toolbar(title = title, showDropDown = false) {
      navController.popBackStack()
    }
  },
    drawerContent = {
      Box(modifier = Modifier.fillMaxSize()){
        Text(text = "Not Implemented Yet. :]", modifier = Modifier.align(Alignment.Center))
      }
    }, drawerGesturesEnabled = true, backgroundColor = backGroundColor()
  ) {
    Box(modifier = Modifier.fillMaxSize()) {
      getLandPadState.apply {
        when (this) {
          is UIState.DataLoaded -> {
            // Get thumbnail of spaceX website
            LazyColumn(verticalArrangement = Arrangement.spacedBy(dp3), state = listState) {
              itemsIndexed(response.landpads!!, key = {index,landPad -> landPad!!.id!!}) { index, landPad ->
                val (isExpanded,setIsExpanded) =  remember {
                  mutableStateOf(false)
                }
                LandPadItem(landPad = landPad!!,isExpanded,setIsExpanded)

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
              landPadViewModel.retry()
            }
          }
          is UIState.SomethingWentWrong -> {
            RetryView(boxScope = this@Box) {
              landPadViewModel.retry()
            }
          }
        }
      }
    }
  }
}

@Composable
fun LandPadItem(landPad: LandpadsQuery.Landpad,isExpanded:Boolean,setIsExpanded:(Boolean) -> Unit) {

  Box(
    modifier = Modifier
      .fillMaxWidth()
      .padding(5.dp)
      .background(color = spaceGreenTransluscent, shape = RoundedCornerShape(5.dp))
  ) {

    Column(verticalArrangement = Arrangement.spacedBy(6.dp), modifier = Modifier.padding(10.dp)) {

      Box(
        modifier = Modifier
          .fillMaxWidth()
          .height(20.dp)
      ) {
        Icon(
          painter = painterResource(id = R.drawable.ic_lauchpad), contentDescription = "",
          modifier = Modifier
            .size(24.dp)
        )
      }



      Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Text(
          text = landPad.full_name ?: "",
          fontSize = listItemMainTextSize,
          color = Color.Black,
          fontWeight = FontWeight.Bold,
          modifier = Modifier.weight(1F)
        )
        IconButton(onClick = {
          setIsExpanded(!isExpanded)
        }, modifier = Modifier.wrapContentSize()) {
          Icon(Icons.Default.MoreHoriz, contentDescription = "")
        }


      }

      AnimatedVisibility(visible = isExpanded) {
        Text(text = landPad.details ?: "", fontSize = SupportingTextSize, modifier = Modifier.alpha(0.9F))
      }
    }
  }
}