package jc.iakakpo.spacejam.ui.screens.viewhelpers

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import jc.iakakpo.spacejam.ui.theme.dp20
import jc.iakakpo.spacejam.ui.theme.listItemMainTextSize
import jc.iakakpo.spacejam.ui.theme.listItemSupportingTextSize
import jc.iakakpo.spacejam.ui.theme.titleTextSize

/**
 * @author Isaac Akakpo
 * Created on 3/7/2022 4:25 PM
 */
@Composable
fun TitleText(text: String, color: Color) {
  Text(
    text = text,
    fontSize = titleTextSize,
    modifier = Modifier.padding(vertical = dp20)
  )
}

@Composable
fun ListItemMainText(text: String, color: Color = Color.Black) {
  Text(
    text = text,
    fontSize = listItemMainTextSize,
    color = color,
    fontWeight = FontWeight.Bold
  )
}

@Composable
fun ListItemSupportText(text: String, color: Color = Color.Black) {
  Text(
    text = text,
    fontSize = listItemSupportingTextSize,
    color = color
  )
}




