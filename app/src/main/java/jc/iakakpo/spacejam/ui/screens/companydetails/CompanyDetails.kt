package jc.iakakpo.spacejam.ui.screens.companydetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import jc.iakakpo.spacejam.CompanyQuery
import jc.iakakpo.spacejam.R
import jc.iakakpo.spacejam.models.Links
import jc.iakakpo.spacejam.ui.screens.viewhelpers.Toolbar
import jc.iakakpo.spacejam.ui.theme.*
import jc.iakakpo.spacejam.utils.asMap

/**
 * @author Isaac Akakpo
 * Created on 3/14/2022 7:55 PM
 */

@Composable
fun CompanyDetails(navController: NavController, companyDetailsViewModel: CompanyDetailsViewModel) {

    val companyDetails by remember(key1 = companyDetailsViewModel) { companyDetailsViewModel.getCompanyDetails() }
        .collectAsState(null)

    Scaffold(topBar = {
        Toolbar(title = "Past Launches", showDropDown = false) {
            navController.popBackStack()
        }
    }, backgroundColor = backGroundColor()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = dp10),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painter = painterResource(id = R.drawable.error_img), contentScale = ContentScale.Crop,
                contentDescription = "",
                modifier = Modifier.size(200.dp)
            )

            Text(text = "Space X", fontSize = titleTextSize, modifier = Modifier.padding(top = dp10))

            if (companyDetails != null) {
                companyDetails!!.asMap().forEach { (key, value) ->
                    when {
                        key.contains("links") || key.contains("Head") -> {
                            DetailsItem(title = key, value = null,true, headquarters = value as CompanyQuery.Headquarters, links = value as CompanyQuery.Links)
                        }
                        else -> {
                            DetailsItem(title = key, value = value as String)
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
    headquarters: CompanyQuery.Headquarters? = null,
    links: CompanyQuery.Links? = null
) {
    Column() {
        Box(modifier = Modifier.background(color = spaceBlueLightTrans)) {
            Text(
                text = title,
                fontSize = contextTextSize,
                modifier = Modifier.padding(vertical = dp5, horizontal = dp15)
            )
        }

        Divider(
            modifier = Modifier
                .padding(vertical = dp5)
                .background(spaceBlueLight)
                .fillMaxWidth()
        )

        if (includesFields) {
            Column(verticalArrangement = Arrangement.spacedBy(dp5)) {
                Text(text = headquarters?.city ?: "Website : " + links?.website ?: "", fontSize = normalTextSize)
                Text(text = headquarters?.state ?: "Twitter : " + links?.twitter ?: "", fontSize = normalTextSize)
                Text(text = headquarters?.address ?: "Flickr : " + links?.flickr ?: "", fontSize = normalTextSize)
            }
        }else{
            Text(text = value ?: "", fontSize = normalTextSize)
        }
    }
}
