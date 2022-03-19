package jc.iakakpo.spacejam.repository

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import jc.iakakpo.spacejam.CompanyDetails
import jc.iakakpo.spacejam.CompanyQuery
import jc.iakakpo.spacejam.PastLaunchesQuery
import jc.iakakpo.spacejam.models.SpaceJamDb
import jc.iakakpo.spacejam.utils.makeClientRequest
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * @author Isaac Akakpo
 * Created on 3/5/2022 3:29 PM
 *
 *
 */
class SpaceJamRepositoryImpl @Inject constructor(private val client: ApolloClient,db:SpaceJamDb) :
  SpaceJamRepository {

  private val queries = db.spaceJamDb().companyDetailsQueries

  override suspend fun getLaunchesPast(limit: Int) = flow {
    emit(makeClientRequest(client.query(PastLaunchesQuery(Optional.presentIfNotNull(limit)))))
  }

  override suspend fun getCompany() = flow {
    emit(makeClientRequest(client.query(CompanyQuery())))
  }

  override suspend fun insertCompanyLocal(companyDetails: CompanyDetails) {
    queries.inserCompany(companyDetails)
  }

  override suspend fun getCompanyLocal(): CompanyDetails? {
    return queries.getCompany().executeAsOneOrNull()
  }


}