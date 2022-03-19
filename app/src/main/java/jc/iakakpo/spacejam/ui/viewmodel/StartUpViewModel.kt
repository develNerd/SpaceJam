package jc.iakakpo.spacejam.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jc.iakakpo.spacejam.CompanyDetails
import jc.iakakpo.spacejam.CompanyQuery
import jc.iakakpo.spacejam.repository.SpaceJamRepository
import jc.iakakpo.spacejam.utils.ClientResult
import jc.iakakpo.spacejam.utils.UIState
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

/**
 * @author Isaac Akakpo
 * Created on 3/14/2022 7:59 PM
 */
@HiltViewModel
class StartUpViewModel @Inject constructor(private val spaceJamRepository: SpaceJamRepository) :
  ViewModel() {


  /**
   * Fetch Company Details on Start Up Client Result form
   * Repository in using the viewModel Scope
   *
   * */
  fun getCompanyDetails(): Flow<UIState<CompanyQuery.Data>> = flow {
    spaceJamRepository.getCompany().collect { result ->
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


  fun saveCompanyDetailsLocal(companyDetails: CompanyDetails?){
    viewModelScope.launch {
      if (companyDetails != null){
        spaceJamRepository.insertCompanyLocal(companyDetails)
      }
    }
  }

  /**
   * Retry if api Error or No-Internet Error
   *
   * */
  fun retry(){
    getCompanyDetails().retry(1)
  }


}