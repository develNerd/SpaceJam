package jc.iakakpo.spacejam

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.*
import jc.iakakpo.spacejam.ui.theme.*
import jc.iakakpo.spacejam.utils.gotoActivity
import kotlinx.coroutines.delay

class DataLoadingActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SpaceJamTheme() {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = backGroundColor()), contentAlignment = Alignment.Center
                ) {
                    var loadingText by remember {
                        mutableStateOf("Loading.....")
                    }

                    var textColor by remember {
                        mutableStateOf(textColor())
                    }

                    LaunchedEffect(key1 = true) {
                        delay(1500)
                        loadingText = "Almost Ready.."
                        textColor = spaceGreenLight
                        delay(800)
                        this@DataLoadingActivity.gotoActivity<MainActivity>()
                        startActivity(Intent(this@DataLoadingActivity, MainActivity::class.java))
                    }


                    Column(
                        modifier = Modifier.wrapContentSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        RocketLoader()
                        LoadingStatus(loadingText = loadingText, textColor = textColor)
                    }
                }
            }
        }
    }
}

@Composable
fun LoadingStatus(loadingText: String, textColor: Color = textColor()) {
    Text(
        text = loadingText,
        color = textColor,
        textAlign = TextAlign.Center,
        fontSize = 21.sp,
        fontFamily = FontFamily.SansSerif,
        fontStyle = FontStyle.Italic
    )
}

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

