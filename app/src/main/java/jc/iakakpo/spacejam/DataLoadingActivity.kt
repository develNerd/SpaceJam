package jc.iakakpo.spacejam

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.OutlinedButton
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
import jc.iakakpo.spacejam.ui.theme.SpaceJamTheme
import jc.iakakpo.spacejam.ui.theme.backGroundColor
import jc.iakakpo.spacejam.ui.theme.spaceGreenLight
import jc.iakakpo.spacejam.ui.theme.textColor
import jc.iakakpo.spacejam.ui.viewmodel.StartUpViewModel
import jc.iakakpo.spacejam.utils.UIState
import jc.iakakpo.spacejam.utils.gotoActivity
import jc.iakakpo.spacejam.utils.parseToCompanyDetails
import kotlinx.coroutines.delay

class DataLoadingActivity : ComponentActivity() {

    private val startUpViewModel by viewModels<StartUpViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            SpaceJamTheme() {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = backGroundColor()),
                    contentAlignment = Alignment.Center
                ) {

                    var loadingText by remember {
                        mutableStateOf("Loading.....")
                    }

                    var textColor by remember {
                        mutableStateOf(textColor())
                    }

                    val companyState by remember(key1 = startUpViewModel) { startUpViewModel.getCompanyDetails() }
                        .collectAsState(initial = UIState.Loading)


                    companyState.apply {
                        when (this) {
                            is UIState.DataLoaded -> {
                                startUpViewModel.saveCompanyDetailsLocal(response.company?.parseToCompanyDetails()).also {
                                    this@DataLoadingActivity.gotoActivity<MainActivity>()
                                    startActivity(Intent(this@DataLoadingActivity, MainActivity::class.java))
                                }
                            }
                            is UIState.Loading -> {
                                Column(
                                    modifier = Modifier.wrapContentSize(),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    RocketLoader()
                                    LoadingStatus(loadingText = loadingText, textColor = textColor)
                                }
                            }
                            is UIState.NoInternet -> {
                                RetryView(boxScope = this@Box){
                                    startUpViewModel.retry()
                                }
                            }
                            is UIState.SomethingWentWrong -> {
                              RetryView(boxScope = this@Box){
                                  startUpViewModel.retry()
                              }
                            }
                        }
                    }



                   /* LaunchedEffect(key1 = true) {
                        delay(1500)
                        textColor = spaceGreenLight
                        delay(800)

                    }*/


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
fun RetryView(boxScope: BoxScope,onRetry:() -> Unit) {
    boxScope.apply {
        Column(modifier = Modifier.align(Alignment.Center), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Something Went Wrong", modifier = Modifier.align(Alignment.CenterHorizontally))
            OutlinedButton(onClick = { }) {
                Text(text = "Retry", modifier = Modifier.padding(horizontal = 15.dp))
            }
        }
    }
}
