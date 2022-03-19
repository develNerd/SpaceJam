package jc.iakakpo.spacejam.ui.screens.viewhelpers

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyGridScope
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.core.content.ContextCompat
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import jc.iakakpo.spacejam.ui.screens.pastlaunches.End
import jc.iakakpo.spacejam.utils.BACK_OFF_DELAY_IN_MILLIS
import kotlinx.coroutines.delay
import timber.log.Timber

/**
 * @author Isaac Akakpo
 * Created on 3/18/2022 1:05 AM
 */
@OptIn(ExperimentalFoundationApi::class)
fun <T : Any> LazyGridScope.items(
  items: LazyPagingItems<T>,
  itemContent: @Composable LazyItemScope.(value: T?) -> Unit
) {

  items(
    count = items.itemCount
  ) { index ->

    itemContent(items[index])
  }
}

@ExperimentalFoundationApi
@Composable
fun <T : Any> HandleLoadingMoreData(
  stateView: @Composable () -> Unit,
  errorView: @Composable () -> Unit,
  pagingItems: LazyPagingItems<T>,
  manualRefresh: Boolean,
  setManualRefresh: (Boolean) -> Unit
) {

  pagingItems.apply {
    when {
      loadState.refresh is LoadState.Loading || loadState.append is LoadState.Loading -> stateView()
      loadState.refresh is LoadState.Error && !manualRefresh -> errorView()
      loadState.refresh is LoadState.Error && manualRefresh -> End()
      loadState.append is LoadState.NotLoading -> End()
      loadState.append is LoadState.Error -> {
        setManualRefresh(true)
        End()
        LaunchedEffect(true) {
          while (true) {
            delay(BACK_OFF_DELAY_IN_MILLIS)
            retry()
          }
        }
      }
      else -> End()
    }
    Timber.e("${pagingItems.loadState}")
  }
}

fun Modifier.onLinkClick(text: String?, context: Context) = composed {
  this.clickable {
    if (!text.isNullOrEmpty() && text.contains("https")) {
      val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(text))
      ContextCompat.startActivity(context, browserIntent, null)
    }
  }
}
