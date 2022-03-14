package jc.iakakpo.spacejam.utils

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyGridScope
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import jc.iakakpo.spacejam.ui.screens.End
import kotlinx.coroutines.delay
import timber.log.Timber

/**
 * @author Isaac Akakpo
 * Created on 2/28/2022 9:19 AM
 */



/*
* Extended Generic function to easily launch an
* intent to another activity. we use reified with inline
* to easily cast the specified generic type as a class.
*
* */
inline fun <reified T> Context.gotoActivity(
    clearStack: Boolean = false,
    intentPacking: ((Intent) -> Unit) = {},
) {
    val intent = Intent(this, T::class.java)
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

    if (clearStack) {
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }

    intentPacking(intent)
    intentPacking.invoke(intent)
    startActivity(intent)
}

val randoms = listOf<String>(
    "https://cdn.pixabay.com/photo/2018/08/15/13/10/galaxy-3608029_1280.jpg",
    "https://cdn.pixabay.com/photo/2016/10/20/18/35/earth-1756274_1280.jpg",
    "https://cdn.pixabay.com/photo/2016/03/18/15/02/ufo-1265186_1280.jpg"
)

@OptIn(ExperimentalFoundationApi::class)
fun <T : Any> LazyGridScope.items(items: LazyPagingItems<T>,
                                  itemContent: @Composable LazyItemScope.(value: T?) -> Unit) {

    items(
        count = items.itemCount
    ) { index ->

        itemContent(items[index])
    }

}



@ExperimentalFoundationApi
@Composable
fun <T : Any> HandleLoadingMoreData(stateView: @Composable () -> Unit, errorView: @Composable () -> Unit, pagingItems: LazyPagingItems<T>,manualRefresh:Boolean,setManualRefresh:(Boolean)-> Unit) {

    pagingItems.apply {
        when {
            loadState.refresh is LoadState.Loading || loadState.append is LoadState.Loading -> stateView()
            loadState.refresh is LoadState.Error && !manualRefresh -> errorView()
            loadState.refresh is LoadState.Error && manualRefresh -> End()
            loadState.append is LoadState.NotLoading -> End()
            loadState.append is LoadState.Error -> {
                setManualRefresh(true)
                End()
                LaunchedEffect(true){
                    while(true) {
                        delay(BACK_OFF_DELAY_IN_MILLIS)
                        retry()
                    }

                }
            }
            else  ->  End()
        }
        Timber.e("${pagingItems.loadState}")



    }

}


