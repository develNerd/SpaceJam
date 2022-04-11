package jc.iakakpo.spacejam.repository

import com.apollographql.apollo3.api.ApolloResponse
import jc.iakakpo.spacejam.*
import jc.iakakpo.spacejam.utils.ClientResult
import kotlinx.coroutines.flow.Flow

/**
 * @author Isaac Akakpo
 * Created on 3/5/2022 3:13 PM
 *
 *  Interface to be implemented by [SpaceJamRepositoryImpl]
 *
 */
interface SpaceJamRepository {
  suspend fun getLaunchesPast(limit: Int): Flow<ClientResult<ApolloResponse<PastLaunchesQuery.Data>>>

  suspend fun getCompany(): Flow<ClientResult<ApolloResponse<CompanyQuery.Data>>>


  fun getMissions():Flow<ClientResult<ApolloResponse<MissionsQuery.Data>>>

  fun getLandPads():Flow<ClientResult<ApolloResponse<LandpadsQuery.Data>>>

  fun getShips():Flow<ClientResult<ApolloResponse<ShipsQuery.Data>>>


  /**
   * Suspend function to save Company Details into SpaceJamDatabase
   * */
  suspend fun insertCompanyLocal(companyDetails: CompanyDetails)

  suspend fun getCompanyLocal(): CompanyDetails?



}