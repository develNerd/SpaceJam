package jc.iakakpo.spacejam

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.*
import jc.iakakpo.spacejam.ui.theme.SpaceJamTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SpaceJamTheme {
                // A surface container using the 'background' color from the theme

            }
        }
    }
}


@Composable
fun SpaceMainPage(){
    Scaffold(topBar =
    {
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            RocketLoaderTitle()
            Icon(
                painter = painterResource(id = R.drawable.ic_space_icon),
                contentDescription = "",
                modifier = Modifier
                    .wrapContentWidth()
                    .height(30.dp)
            )
        }
    }, modifier = Modifier.fillMaxSize()
    )
    {

    }
}


@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Composable
fun RocketLoaderTitle() {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.rocket_in_space))
    val progress by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever,
    )
    LottieAnimation(
        composition,
        progress, modifier = Modifier.size(30.dp)
    )
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SpaceJamTheme {
        Greeting("Android")
    }
}