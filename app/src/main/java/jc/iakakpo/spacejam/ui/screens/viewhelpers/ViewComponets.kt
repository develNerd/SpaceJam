package jc.iakakpo.spacejam.ui.screens.viewhelpers

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.*
import jc.iakakpo.spacejam.R
import jc.iakakpo.spacejam.ui.theme.*

/**
 * @author Isaac Akakpo
 * Created on 3/18/2022 5:18 PM
 */

@Composable
fun Toolbar(title: String, showDropDown: Boolean, onMenuClick: () -> Unit = {}, onNavigateBack: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = spaceLightGreen),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = {
            onNavigateBack()
        }) {
            Icon(painterResource(id = R.drawable.ic_back), contentDescription = "")
        }
        Text(
            text = title,
            fontSize = titleTextSize,
            modifier = Modifier.padding(vertical = dp20)
        )

        if (showDropDown) {

        }
    }
}


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

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier.fillMaxWidth()) {


      Box(
        Modifier
          .size(30.dp)
          .padding(4.dp)
          .align(Alignment.CenterHorizontally)
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


/**
 *
 * Rocker loader page for when data is loading
 * */
@Composable
fun RocketLoader(modifier: Modifier = Modifier) {
  val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.rocket_in_space))
  val progress by animateLottieCompositionAsState(
    composition,
    iterations = LottieConstants.IterateForever,
  )
  LottieAnimation(
    composition,
    progress, modifier = modifier.size(100.dp)
  )
}

/**
 *
 * Retry view to retry if Api Call isn't successful
 * */
@Composable
fun RetryView(boxScope: BoxScope, onRetry: () -> Unit) {
  boxScope.apply {
    Column(modifier = Modifier.align(Alignment.Center), horizontalAlignment = Alignment.CenterHorizontally) {
      Text(text = "Something Went Wrong", modifier = Modifier.align(Alignment.CenterHorizontally))
      OutlinedButton(onClick = {onRetry() }) {
        Text(text = "Retry", modifier = Modifier.padding(horizontal = 15.dp))
      }
    }
  }
}
