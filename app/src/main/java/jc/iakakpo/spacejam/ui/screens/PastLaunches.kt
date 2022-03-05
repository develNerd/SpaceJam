package jc.iakakpo.spacejam.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import jc.iakakpo.spacejam.R
import jc.iakakpo.spacejam.ui.theme.dp20
import jc.iakakpo.spacejam.ui.theme.spaceLightGreen
import jc.iakakpo.spacejam.ui.theme.titleTextSize

/**
 * @author Isaac Akakpo
 * Created on 3/1/2022 9:32 AM
 */
@Preview(showBackground = true)
@Composable
fun PastLaunchesPage(){
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(modifier = Modifier
                .fillMaxWidth()
                .background(color = spaceLightGreen), verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon( painterResource(id = R.drawable.ic_back), contentDescription = "")
                }
                Text(text = "Past Launches", fontSize = titleTextSize, modifier = Modifier.padding(vertical = dp20))
            }
        }
    }
}
