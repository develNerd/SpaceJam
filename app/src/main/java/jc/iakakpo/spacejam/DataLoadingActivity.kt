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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.airbnb.lottie.compose.*
import dagger.hilt.android.AndroidEntryPoint
import jc.iakakpo.spacejam.ui.screens.viewhelpers.RetryView
import jc.iakakpo.spacejam.ui.screens.viewhelpers.RocketLoader
import jc.iakakpo.spacejam.ui.theme.SpaceJamTheme
import jc.iakakpo.spacejam.ui.theme.backGroundColor
import jc.iakakpo.spacejam.ui.theme.textColor
import jc.iakakpo.spacejam.ui.viewmodel.StartUpViewModel
import jc.iakakpo.spacejam.utils.PrefManager
import jc.iakakpo.spacejam.utils.UIState
import jc.iakakpo.spacejam.utils.gotoActivity
import jc.iakakpo.spacejam.utils.parseToCompanyDetails
import javax.inject.Inject

@AndroidEntryPoint
class DataLoadingActivity : ComponentActivity() {

    @Inject
    lateinit var prefManager: PrefManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        if (prefManager.isCompanyDetailsSaved){
            this@DataLoadingActivity.gotoActivity<MainActivity>(true)
        }
        val startUpViewModel: StartUpViewModel by viewModels()

        setContent {

            SpaceJamTheme() {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = backGroundColor()),
                    contentAlignment = Alignment.Center
                ) {

                    val loadingText by remember {
                        mutableStateOf("Loading.....")
                    }

                    val textColor by remember {
                        mutableStateOf(textColor())
                    }

                    val companyState by remember(key1 = startUpViewModel) { startUpViewModel.getCompanyDetails() }
                        .collectAsState(initial = UIState.Loading)

                    companyState.apply {
                        when (this) {
                            is UIState.DataLoaded -> {
                                startUpViewModel.saveCompanyDetailsLocal(response.company?.parseToCompanyDetails()).also {
                                    prefManager.isCompanyDetailsSaved = true
                                    this@DataLoadingActivity.gotoActivity<MainActivity>()
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
                                RetryView(boxScope = this@Box) {
                                    startUpViewModel.retry()
                                }
                            }
                            is UIState.SomethingWentWrong -> {
                                RetryView(boxScope = this@Box) {
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
