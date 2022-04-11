package jc.iakakpo.spacejam.ui.screens.launchPads

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jc.iakakpo.spacejam.LandpadsQuery
import jc.iakakpo.spacejam.repository.SpaceJamRepository
import jc.iakakpo.spacejam.utils.ClientResult
import jc.iakakpo.spacejam.utils.RetryTrigger
import jc.iakakpo.spacejam.utils.UIState
import jc.iakakpo.spacejam.utils.retryableFlow
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import timber.log.Timber
import javax.inject.Inject

/**
 * @author Isaac Akakpo
 * Created on 3/29/2022 6:52 PM
 */

@HiltViewModel
class LandPadViewModel @Inject constructor(
  private val spaceJamRepository: SpaceJamRepository,
) : ViewModel() {

  private val retryTrigger = RetryTrigger()

  /**
   * Get Missions List in @param[retryTrigger] impelemted
   * for calling flow again
   *
   * */
  @OptIn(FlowPreview::class)
  fun getLaunchPads(): Flow<UIState<LandpadsQuery.Data>> = retryableFlow(retryTrigger) {
    UIState.Loading
    return@retryableFlow spaceJamRepository.getLandPads().map {
      when (it) {
        is ClientResult.Error -> {
          Timber.e(it.error)
          UIState.SomethingWentWrong(it.error)
        }
        is ClientResult.HttpError -> {
          Timber.e(it.error)
          UIState.SomethingWentWrong(error = it.error)
        }
        is ClientResult.InProgress -> {
          UIState.Loading
        }
        is ClientResult.NoInternet -> {
          Timber.e(it.error)
          UIState.NoInternet(it.error)
        }
        is ClientResult.Success -> {
          UIState.DataLoaded(it.response.data!!)
        }
      }
    }
  }




  /**
   * Retry if api Error or No-Internet Error
   *
   * */
  fun retry() {
    retryTrigger.retry()
  }

}