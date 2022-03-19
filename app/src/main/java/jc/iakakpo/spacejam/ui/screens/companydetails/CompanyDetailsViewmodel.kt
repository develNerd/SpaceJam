package jc.iakakpo.spacejam.ui.screens.companydetails

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jc.iakakpo.spacejam.CompanyDetails
import jc.iakakpo.spacejam.repository.SpaceJamRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * @author Isaac Akakpo
 * Created on 3/18/2022 10:27 PM
 */

@HiltViewModel
class CompanyDetailsViewModel @Inject constructor(private val spaceJamRepository: SpaceJamRepository) : ViewModel() {


  fun getCompanyDetails() : Flow<CompanyDetails?> = flow {
    emit(spaceJamRepository.getCompanyLocal())
  }

}