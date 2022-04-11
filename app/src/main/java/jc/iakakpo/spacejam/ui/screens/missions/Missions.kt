package jc.iakakpo.spacejam.ui.screens.missions

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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import jc.iakakpo.spacejam.*
import jc.iakakpo.spacejam.R
import jc.iakakpo.spacejam.ui.screens.viewhelpers.RetryView
import jc.iakakpo.spacejam.ui.screens.viewhelpers.RocketLoader
import jc.iakakpo.spacejam.ui.screens.viewhelpers.Toolbar
import jc.iakakpo.spacejam.ui.theme.*
import jc.iakakpo.spacejam.utils.UIState
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*

/**
 * @author Isaac Akakpo
 * Created on 3/20/2022 3:08 PM
 */

/**
 * Missions page to view all mission
 *
 * */
@Composable
fun Missions(navController: NavController, missionsViewModel: MissionsViewModel, title: String) {

    val missionsState by remember { missionsViewModel.getMissions() }
        .collectAsState(UIState.Loading)

    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    Scaffold(scaffoldState =scaffoldState,topBar = {
        Toolbar(title = title, showDropDown = false) {
            navController.popBackStack()
        }
    },
        drawerContent = {
            Box(modifier = Modifier.fillMaxSize()){
                Text(text = "Not Implemented Yet. :]", modifier = Modifier.align(Alignment.Center))
            }
        }, drawerGesturesEnabled = true, backgroundColor = backGroundColor()) {
        Box(modifier = Modifier.fillMaxSize()) {
            val state = rememberLazyListState()
            missionsState.apply {
                when (this) {
                    is UIState.DataLoaded -> {
                        // Get thumbnail of spaceX website
                        LazyColumn(verticalArrangement = Arrangement.spacedBy(dp3), state = state) {
                            itemsIndexed(response.missions!!.toTypedArray()) { index, mission ->
                                MissionItem(mission = mission!!){
                                    scope.launch {
                                        scaffoldState.drawerState.apply {
                                            if (isClosed) open() else close()
                                        }
                                    }
                                }
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
                            missionsViewModel.retry()
                        }
                    }
                    is UIState.SomethingWentWrong -> {
                        RetryView(boxScope = this@Box) {
                            missionsViewModel.retry()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MissionItem(mission: MissionsQuery.Mission,onClickMore:() -> Unit) {

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
                Icon(
                    painter = painterResource(id = R.drawable.ic_next_launches), contentDescription = "",
                    modifier = Modifier
                        .size(24.dp)
                        .align(
                            Alignment.Center
                        )
                )
            }
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = mission.name ?: "",
                    fontSize = listItemMainTextSize,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1F)
                )
                IconButton(onClick = {
                                     onClickMore()
                }, modifier = Modifier.wrapContentSize()) {
                    Icon(Icons.Default.MoreHoriz, contentDescription = "")
                }
            }
        }
    }
}
