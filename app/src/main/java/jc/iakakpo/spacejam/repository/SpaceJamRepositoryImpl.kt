package jc.iakakpo.spacejam.repository

import android.content.Context
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.api.Optional
import dagger.hilt.android.qualifiers.ApplicationContext
import jc.iakakpo.spacejam.*
import jc.iakakpo.spacejam.models.SpaceJamDb
import jc.iakakpo.spacejam.utils.ClientResult
import jc.iakakpo.spacejam.utils.makeClientRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * @author Isaac Akakpo
 * Created on 3/5/2022 3:29 PM
 *
 *
 */
class SpaceJamRepositoryImpl @Inject constructor(@ApplicationContext appContext: Context, private val client: ApolloClient, db:SpaceJamDb) :
  SpaceJamRepository {

  /**
   * Get Generated query from the [SpaceJamDb]
   * */
  private val queries = db.spaceJamDb(appContext).companyDetailsQueries

  /**
   * @see[SpaceJamModule]
   * Get past Launches using injected [client] and a query
   * */
  override suspend fun getLaunchesPast(limit: Int) = flow {
    emit(makeClientRequest(client.query(PastLaunchesQuery(Optional.presentIfNotNull(limit)))))
  }

  override suspend fun getCompany() = flow {
    emit(makeClientRequest(client.query(CompanyQuery())))
  }

  override fun getMissions(): Flow<ClientResult<ApolloResponse<MissionsQuery.Data>>> = flow {
    emit(ClientResult.InProgress)
    emit(makeClientRequest(client.query(MissionsQuery())))
  }

  override suspend fun getLandPads(): Flow<ClientResult<ApolloResponse<LandpadsQuery.Data>>> {
    TODO("Not yet implemented")
  }

  override suspend fun getShips(): Flow<ClientResult<ApolloResponse<LandpadsQuery.Data>>> {
    TODO("Not yet implemented")
  }

  override suspend fun insertCompanyLocal(companyDetails: CompanyDetails) {
    queries.inserCompany(companyDetails)
  }

  override suspend fun getCompanyLocal(): CompanyDetails {
    return queries.getCompany().executeAsList()[0]
  }


}