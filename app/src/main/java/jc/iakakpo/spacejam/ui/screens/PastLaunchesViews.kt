package jc.iakakpo.spacejam.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import jc.iakakpo.spacejam.ui.theme.spaceGreenTransluscent
import jc.iakakpo.spacejam.ui.theme.spaceLightGreen

/**
 * @author Isaac Akakpo
 * Created on 3/12/2022 8:19 AM
 */
@Composable
fun End(){
  Column(modifier = Modifier.fillMaxWidth().padding(vertical = 30.dp)) {
    Box(modifier = Modifier
        .height(1.dp)
        .padding(top = 5.dp)
        .fillMaxWidth()
        .background(color = Color.Black))
    Box(modifier = Modifier
        .size(5.dp)
        .align(Alignment.CenterHorizontally)
        .background(color = spaceLightGreen, shape = RoundedCornerShape(100)))
  }

}
