package jc.iakakpo.spacejam.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import jc.iakakpo.spacejam.ui.theme.*
import jc.iakakpo.spacejam.utils.HUNDRED
import jc.iakakpo.spacejam.utils.TEN

/**
 * @author Isaac Akakpo
 * Created on 2/24/2022 11:23 PM
 */
@Composable
fun MainDashboardItem(name: String, icon: Int, rowScope: RowScope, onClick: () -> Unit) {
    rowScope.apply {

        Column(
            modifier = Modifier
                .clickable(
                    indication = rememberRipple(bounded = true),
                    interactionSource = MutableInteractionSource(),
                    onClick = {
                        onClick()
                    })
                .weight(0.5F)
                .background(color = spaceGreenLightTrans, shape = RoundedCornerShape(TEN)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .padding(top = dp15)
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(HUNDRED)
                    )
                    .size(roundItemsSize), contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = icon),
                    contentDescription = "",
                    modifier = Modifier.size(
                        size32
                    )
                )
            }

            Text(
                text = name,
                color = colorPrimary,
                textAlign = TextAlign.Center,
                fontSize = contextTextSize,
                fontFamily = FontFamily.SansSerif,
                fontStyle = FontStyle.Italic,
                modifier = Modifier.padding(top = dp35, bottom = dp10)
            )

        }
    }


}