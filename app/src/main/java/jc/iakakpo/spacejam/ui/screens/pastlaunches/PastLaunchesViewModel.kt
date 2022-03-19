package jc.iakakpo.spacejam.ui.screens.pastlaunches

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.apollographql.apollo3.ApolloClient
import dagger.hilt.android.lifecycle.HiltViewModel
import jc.iakakpo.spacejam.PastLaunchesQuery
import jc.iakakpo.spacejam.repository.PaginatedPastLaunchesRepository
import jc.iakakpo.spacejam.repository.SpaceJamRepository
import jc.iakakpo.spacejam.utils.ClientResult
import jc.iakakpo.spacejam.utils.UIState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject

/**
 * @author Isaac Akakpo
 * Created on 3/5/2022 4:33 PM
 *
 * Past Launches ViewModel for PastLaunches Screen.
 * Inject as an Argument to the Composable Function
 */
@HiltViewModel
class PastLaunchesViewModel @Inject constructor(
  private val spaceJamRepository: SpaceJamRepository,
  private val apolloClient: ApolloClient
) :
  ViewModel() {


  /**
   * Flow
   */
  val paginatedPastLaunches = Pager(
    // Configure how data is loaded by passing additional properties to
    // PagingConfig, such as prefetchDistance.
    PagingConfig(pageSize = 15)
  ) {
    PaginatedPastLaunchesRepository(client = apolloClient)
  }.flow
    .cachedIn(viewModelScope)


  /**
   * Fetch PastLaunches Client Result form
   * Repository in using the viewModel Scope
   *
   * */
  fun getPastLaunches(): Flow<UIState<PastLaunchesQuery.Data>> = flow {
    spaceJamRepository.getLaunchesPast(20).collect { result ->
      result.apply {

        val state = when (this) {
          is ClientResult.Error -> {
            Timber.e(error.message)
            UIState.SomethingWentWrong
          }
          is ClientResult.HttpError -> {
            Timber.e(error.message)
            UIState.SomethingWentWrong
          }
          is ClientResult.InProgress -> {
            UIState.Loading
          }
          is ClientResult.NoInternet -> {
            UIState.NoInternet(error)
          }
          is ClientResult.Success -> {
            Timber.e("${response.data!!} Success")
            UIState.DataLoaded(response.data!!)
          }
        }
        emit(state)
      }
    }
  }


}