package jc.iakakpo.spacejam.enums

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import dagger.hilt.android.qualifiers.ApplicationContext
import jc.iakakpo.spacejam.R
import javax.inject.Inject

/**
 * @author Isaac Akakpo
 * Created on 2/28/2022 5:54 PM
 *
 *
 *
 */


class StringResourceProvider @Inject constructor ( @ApplicationContext private val context: Context) {

  fun mainPage(): String = context.getString(R.string.mainPage)
  fun pastLaunches(): String = context.getString(R.string.pastLaunches)
  fun companyDetails(): String = context.getString(R.string.companyDetails)
  fun ships(): String = context.getString(R.string.ships)
  fun landPads(): String = context.getString(R.string.landPads)
  fun missions(): String = context.getString(R.string.missions)

}