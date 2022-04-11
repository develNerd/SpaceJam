package jc.iakakpo.spacejam.ui.screens.missions

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import dagger.hilt.android.lifecycle.HiltViewModel
import jc.iakakpo.spacejam.MissionsQuery
import jc.iakakpo.spacejam.repository.SpaceJamRepository
import jc.iakakpo.spacejam.utils.*
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import timber.log.Timber
import javax.inject.Inject

/**
 * @author Isaac Akakpo
 * Created on 3/20/2022 2:39 PM
 */
@HiltViewModel
class MissionsViewModel @Inject constructor(
    private val spaceJamRepository: SpaceJamRepository,
) : ViewModel() {

    private val retryTrigger = RetryTrigger()

    /**
     * Get Missions List in @param[retryTrigger] impelemted
     * for calling flow again
     *
     * */
    @OptIn(FlowPreview::class)
    fun getMissions(): Flow<UIState<MissionsQuery.Data>> = retryableFlow(retryTrigger) {
        UIState.Loading
        return@retryableFlow spaceJamRepository.getMissions().map {
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
