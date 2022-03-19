package jc.iakakpo.spacejam.ui.screens.companydetails

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavController
import jc.iakakpo.spacejam.CompanyQuery
import jc.iakakpo.spacejam.MainActivity
import jc.iakakpo.spacejam.R
import jc.iakakpo.spacejam.ui.screens.viewhelpers.Toolbar
import jc.iakakpo.spacejam.ui.screens.viewhelpers.onLinkClick
import jc.iakakpo.spacejam.ui.theme.*
import jc.iakakpo.spacejam.utils.asMap
import jc.iakakpo.spacejam.utils.castAs

/**
 * @author Isaac Akakpo
 * Created on 3/14/2022 7:55 PM
 */

@Composable
fun Details(navController: NavController, companyDetailsViewModel: CompanyDetailsViewModel) {

    val companyDetails by remember(key1 = companyDetailsViewModel) { companyDetailsViewModel.getCompanyDetails() }
        .collectAsState(null)

    Scaffold(topBar = {
        Toolbar(title = "Company Details", showDropDown = false) {
            navController.popBackStack()
        }
    }, backgroundColor = backGroundColor()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = dp10)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painter = painterResource(id = R.drawable.space_x), contentScale = ContentScale.Crop,
                contentDescription = "",
                modifier = Modifier.size(100.dp).clip(RoundedCornerShape(100)),
            )

            Text(text = "Space X", fontSize = titleTextSize, modifier = Modifier.padding(vertical = dp10))
            val context = LocalContext.current as MainActivity

            if (companyDetails != null) {
                companyDetails!!.asMap().forEach { (key, value) ->
                    when {
                        key.contains("links") || key.contains("Head") -> {
                            DetailsItem(
                                title = key,
                                value = null,
                                true,
                                headquarters = value?.castAs<CompanyQuery.Headquarters>(),
                                links = value?.castAs<CompanyQuery.Links>(),
                                context = context
                            )
                        }
                        else -> {
                            DetailsItem(
                                title = key,
                                value = value.toString(),
                                false,
                                null,
                                null,
                                context = context
                            )
                        }
                    }
                }
            }
//
        }
    }
}


@Composable
fun DetailsItem(
    title: String,
    value: String? = null,
    includesFields: Boolean = false,
    headquarters: CompanyQuery.Headquarters?,
    links: CompanyQuery.Links?,
    context: Context
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(top = dp20)
    ) {
        Box(
            modifier = Modifier.background(
                color = spaceBlueLightTrans,
                shape = RoundedCornerShape(100)
            )
        ) {
            Text(
                text = title,
                fontSize = contextTextSize,
                modifier = Modifier.padding(vertical = dp5, horizontal = dp15)
            )
        }

        if (includesFields) {
            Column(
                verticalArrangement = Arrangement.spacedBy(dp5),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = dp10),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = headquarters?.city ?: "Website : " + links?.website ?: "",
                    fontSize = normalTextSize,
                    textAlign = TextAlign.Center,
                    color = if (links != null) spaceBlueLight else Color.Black,
                    fontStyle = if (links != null) FontStyle.Italic else FontStyle.Normal,
                    modifier = Modifier.onLinkClick(links?.website, context)
                )
                Text(
                    text = headquarters?.state ?: "Twitter : " + links?.twitter ?: "",
                    fontSize = normalTextSize,
                    textAlign = TextAlign.Center,
                    color = if (links != null) spaceBlueLight else Color.Black,
                    fontStyle = if (links != null) FontStyle.Italic else FontStyle.Normal,
                    modifier = Modifier.onLinkClick(links?.twitter, context)
                )
                Text(
                    text = headquarters?.address ?: "Flickr : " + links?.flickr ?: "",
                    fontSize = normalTextSize,
                    textAlign = TextAlign.Center,
                    color = if (links != null) spaceBlueLight else Color.Black,
                    fontStyle = if (links != null) FontStyle.Italic else FontStyle.Normal,
                    modifier = Modifier.onLinkClick(links?.flickr, context)
                )
            }
        } else {
            Text(
                text = value ?: "",
                fontSize = normalTextSize,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(
                    horizontal = dp10, vertical = dp10
                )
            )
        }

        Divider(
            modifier = Modifier
                .padding(vertical = dp15)
                .background(spaceGreenLightTrans)
                .fillMaxWidth()
        )
    }
}

