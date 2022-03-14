package jc.iakakpo.spacejam.ui.screens

/**
 * @author Isaac Akakpo
 * @since [https://github.com/mutualmobile/compose-animation-examples] for reference
 * Created on 3/12/2022 12:06 AM
 */

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import jc.iakakpo.spacejam.ui.theme.backGroundColor
import jc.iakakpo.spacejam.ui.theme.spaceGreenLight
import jc.iakakpo.spacejam.ui.theme.spaceGreenLightTrans

@Preview(showBackground = true)
@Composable
fun PageLoader(modifier: Modifier = Modifier) {
  Box(
      modifier
          .background(backGroundColor())
  ) {

    val infiniteTransition = rememberInfiniteTransition()
    val angle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    Column(horizontalAlignment = CenterHorizontally, modifier = modifier.fillMaxWidth()) {


      Box(
          Modifier
              .size(30.dp)
              .padding(4.dp)
              .align(CenterHorizontally)
      ) {


        Canvas(modifier = Modifier
            .align(Alignment.Center)
            .fillMaxSize(), onDraw = {
          drawCircle(color = spaceGreenLightTrans, style = Stroke(width = 5F))
        })

        Canvas(modifier = Modifier
            .align(Alignment.Center)
            .fillMaxSize(), onDraw = {
          drawArc(
              color = spaceGreenLight,
              style = Stroke(
                  width = 5F,
                  cap = StrokeCap.Round,
                  join =
                  StrokeJoin.Round,
              ),
              startAngle = angle,
              sweepAngle = 360 / 4f,
              useCenter = false
          )
        })
      }


    }
  }
}