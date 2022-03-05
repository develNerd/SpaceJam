package jc.iakakpo.spacejam.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo3.api.ApolloResponse
import jc.iakakpo.spacejam.PastLaunchesQuery
import jc.iakakpo.spacejam.repository.SpaceJamRepository
import jc.iakakpo.spacejam.utils.ClientResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author Isaac Akakpo
 * Created on 3/5/2022 4:33 PM
 *
 * Past Launches ViewModel for PastLaunches Screen.
 * Inject as an Argument to the Composable Function
 */
class PastLaunchesViewModel @Inject constructor(private val spaceJamRepository: SpaceJamRepository) :
    ViewModel() {


    /**
     * Past launches state flow to be observed
     * on Screen UI, returning a given Client Result
     * State
     *
     * */
    private val _pastLaunches =
        MutableStateFlow<ClientResult<ApolloResponse<PastLaunchesQuery.Data>>>(ClientResult.InProgress)
    private val pastLaunches: StateFlow<ClientResult<ApolloResponse<PastLaunchesQuery.Data>>>
        get() = _pastLaunches


    /**
     * Fetch PastLaunches Client Result form
     * Repository in using the viewModel Scope
     *
     * */
    fun getPastLaunches() {
        viewModelScope.launch {
            spaceJamRepository.getLaunchesPast(20).collect { result ->
                _pastLaunches.value = result
            }
        }
    }
}